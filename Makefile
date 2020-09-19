local:
	sbt clean "project example" docker
	docker-compose down --remove-orphans
	docker-compose up --build
