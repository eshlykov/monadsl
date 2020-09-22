local:
	sbt clean "project tracker" docker
	docker-compose down --remove-orphans
	docker-compose up --build
