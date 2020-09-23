import cats.{Monad, Monoid}

package object monadsl {

  sealed trait Condition {
    def and(that: Condition): Condition

    def or(that: Condition): Condition
  }

  class ConditionF[F[_]](val cond: F[Boolean])(implicit F: Monad[F]) extends Condition {
    override def and(that: Condition): Condition = new ConditionF[F](
      F.flatMap(cond) {
        case true => toF(that)
        case false => F.pure(false)
      }
    )

    override def or(that: Condition): Condition = new ConditionF[F](
      F.flatMap(cond) {
        case true => F.pure(true)
        case false => toF(that)
      }
    )

    private def toF(that: Condition): F[Boolean] =
      that match {
        case m: ConditionF[F] => m.cond
        case v: ConditionT[F] => F.pure(v.cond)
      }
  }

  class ConditionT[F[_]](val cond: Boolean)(implicit F: Monad[F]) extends Condition {
    override def and(that: Condition): Condition = if (cond) that else new ConditionT(cond = false)

    override def or(that: Condition): Condition = if (cond) new ConditionT(cond = true) else that
  }

  class Alternative[F[_], T](cond: Condition, ifAction: => F[T])(implicit F: Monad[F]) {
    def otherwise(elseAction: => F[T]): F[T] =
      cond match {
        case m: ConditionF[F] =>
          F.flatMap(m.cond) {
            case true => ifAction
            case false => elseAction
          }
        case v: ConditionT[F] =>
          if (v.cond) ifAction else elseAction
      }
  }

  implicit class Equaler[F[_], T](val action: F[T]) extends AnyVal {
    def is(value: T)(implicit F: Monad[F]): F[Boolean] = F.map(action)(_ == value)
  }

  def when[F[_], T](cond: Condition)(ifAction: => F[T])(implicit F: Monad[F]): Alternative[F, T] = new Alternative(cond, ifAction)

  def not[F[_]](cond: Condition)(implicit F: Monad[F]): Condition =
    cond match {
      case m: ConditionF[F] => new ConditionF(F.map(m.cond)(!_))
      case v: ConditionT[F] => new ConditionT(!v.cond)
    }

  implicit def toConditionF[F[_]](f: F[Boolean])(implicit F: Monad[F]): Condition = new ConditionF(f)

  implicit def toConditionT[F[_]](v: Boolean)(implicit F: Monad[F]): Condition = new ConditionT(v)

  implicit def toF[F[_], T](alternative: Alternative[F, T])(implicit F: Monad[F], T: Monoid[T]): F[T] = alternative.otherwise(F.pure(T.empty))
}
