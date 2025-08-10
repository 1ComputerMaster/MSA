# Makefile

ifeq ($(OS),Windows_NT)
  DC := docker compose
  GRADLE_BUILD := cmd /c ".\gradlew.bat docker"
else
  DC := docker compose
  GRADLE_BUILD := ./gradlew docker
endif

.PHONY: run build up down logs clean

run: build up
	@echo "✅ all up"

build:
	@echo "Running Gradle Docker task"
	@$(GRADLE_BUILD)

up:
	@echo "Running $(DC) up -d"
	@$(DC) up -d

down:
	@$(DC) down

logs:
	@$(DC) logs -f --tail=200

clean:
	@$(DC) down -v
