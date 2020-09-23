package util

import org.scalatest.flatspec.AnyFlatSpec
import util.mock.MockWiringFactory
import util.test.{AssertionUtils, CallHandlerUtils}

trait TestBase extends AnyFlatSpec with CallHandlerUtils with AssertionUtils with MockWiringFactory
