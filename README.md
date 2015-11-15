# restorant

Hello,

it's my test task.You can find some important aspects and curl examples below.

1. Sometimes I don't use best practices, cause it takes a lot of time for test task (for example, I don't use DTO in Rest, maybe with dozer, and don't make really authorization). I'd like to discuss about it in interview.
2. While deploying application create some test data in database. You can use next credentials:
admin / admin (admin user)
user / user (regular user)
super / super (user with both roles)
3. Please find properties file restorant\src\main\resources\application.properties and change information about your DB.
4. In "dist" catalog you can find *.war file.
5. I didn't write javadoc for very simple methods.
6. Auth token generates automatically and expires every 10 hours. So, you can't use token from examples below in your deployment.


curl examples:
1. Get all menues (without auth)
curl http://localhost:8080/restorant/menu/

2. Get token for auth
curl http://localhost:8080/restorant/auth/token/ --header "Content-Type:application/json" -X POST -d "{ \"name\": \"super\", \"password\": \"super\" }"
This command return string with auth token. Use this token in next commands

3. Create new menu
curl http://localhost:8080/restorant/menu/ --header "Content-Type:application/json" --header "X-Auth-Token:eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE0NDc2MTM5ODcsImV4cCI6MTQ0NzY0OTk4Nywic3ViIjoic3VwZXIifQ.qdixr7DxPDea42NPgsWaDsPnA7mXrcrY9XlnRzGJw4A7i67ldR9SBYfQ2jcqVOHBe6aJZOeJzLqC0dnoU3OBjg" -X POST -d "{ \"actualDate\": \"2015-11-25\", \"items\":[{\"name\": \"Dish #100\",\"price\": 50},{\"name\": \"Dish #101\",\"price\": 70}], \"restorant\": {\"name\": \"Restorant In-the-sky\"} }"
Important: you should replace X-Auth-Token header to your own

4. Voting for the menu
curl http://localhost:8080/restorant/menu/vote/1 --header "Content-Type:application/json" --header "X-Auth-Token:eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE0NDc2MTM5ODcsImV4cCI6MTQ0NzY0OTk4Nywic3ViIjoic3VwZXIifQ.qdixr7DxPDea42NPgsWaDsPnA7mXrcrY9XlnRzGJw4A7i67ldR9SBYfQ2jcqVOHBe6aJZOeJzLqC0dnoU3OBjg" -X POST
Important: looking to the tail of endpoint. This is an identifier of Menu entity.


Thank you