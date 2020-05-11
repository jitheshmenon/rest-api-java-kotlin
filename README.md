# SampleApp 

This project comprises two parts:

* The front end application - based on the [React Todo MVC Example](https://github.com/reactjs/redux/tree/master/examples/Todomvc).
* A back end spring-boot application which serves as the API for this example.

The application allows users to:

1. Create new to-do items
2. Edit existing todos (by double clicking)
3. Mark and un-mark a to-do as complete
4. Filter to-dos based on their status
5. Permanently remove (clear) completed to-dos

## Pre-requisites

* A git client (if you're on Windows, [Atlassian SourceTree](http://www.sourcetreeapp.com/) works well)
* A java editor or IDE of your choice (we use [IntelliJ Idea](http://www.jetbrains.com/idea/) but there's nothing here that relies on that)
* Java 1.8+ (it won't work with Java 7 - we use Java 8 syntax)
* Maven 3.5+ (it may work with earlier versions - but you may have issues running the front-end-maven-plugin)
* An internet connection (to download maven dependencies, etc - it's not required once you've done that)

## Getting started

Run `mvn install` to get everything downloaded and make sure you're good on that front.

Run `mvn test` and `mvn com.github.eirslett:frontend-maven-plugin:npm@npm-test` to run the back-end and front-end tests respectively.

Then, using multiple terminals/command windows:

1. Run `mvn spring-boot:run` to start the back end (spring-boot based) server.
   You can access it at [http://localhost:8080/todos](http://localhost:8080/todos) to make sure it's up.
   Alternatively, you can run the Main class from your IDE to support debugging, hotswap, etc as necessary.
2. Run `mvn com.github.eirslett:frontend-maven-plugin:npm@npm-start` to start the front end application.
   You can access it at [http://localhost:3000/](http://localhost:3000/) to make sure it's up.
   Once it's running, you are able to edit the static assets and they will be recompiled and reloaded on the fly - you don't need to restart the front-end server.
   If you'd rather run node directly, you can go to the `src/main/frontend` folder and run `npm start` and `npm test` (and bypass maven completely).

## Database Console

If you want to query the database, you can access [http://localhost:8080/h2-console](http://localhost:8080/h2-console).
You can find the necessary connection details in src/main/resources/application.yml (the username/password will probably be todo/todo).

## API calls

The back end currently exposes the following endpoints (and uses an in-memory List of Maps representing JSON payloads)

| Path             | Method | Reques                              | Response                                        | Description                                                                              |
|-----------------|---------|-------------------------------------|-------------------------------------------------|------------------------------------------------------------------------------------------|
| /todo           | POST    | `{text:String, completed: Boolean}` | `{id:Int, text:String, completed:Boolean}`      | Creates a new Todo                                                                       |
| /todo           | PUT     | `{id:Int, text:String}`             | `{id:Int, text:String, completed:Boolean}`      | Updates a Todo                                                                           |
| /todo           | DELETE  | `{id:Int}`                          | *none*                                          | Deletes a Todo                                                                           |
| /todo/complete  | POST    | `{id:Int}`                          | `{id:Int, text:String, completed:Boolean}`      | Completes a Todo                                                                         |
| /todos          | GET     | *none*                              | `[{id:Int, text:String, completed:Boolean},..]` | Lists all Todos                                                                          |
| /todos/clear    | POST    | *none*                              | `[{id:Int, text:String, completed:Boolean},..]` | Removes completed Todos                                                                  |
| /todos/complete | POST    | *none*                              | `[{id:Int, text:String, completed:Boolean},..]` | Marks all Todos as complete (if any are incomplete), or incomplete (if all are complete) | 

