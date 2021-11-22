# Run bdd tests

On the bdd-test level:
mvn clean install

optionally you can add -Dmetafilter="+issue" to run specific scenario
optionally you can add -Dconfig="${path_to_file}" to run tests with specific config

Report will be generated in:
target/site/serenity/index.html