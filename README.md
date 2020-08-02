# YourTeam

YourTeam is the software to manage your team. We offer you the opportunity to sort your team into categories such as members, groups and further in order to always have an overview and especially control over the team. We have specially designed Yourteam so that the software can easily be changed or managed via our rest api interface, depending on use. You can acquire further and more detailed information on individual parts of the software in the course of this document.

## Build Status
|             | Build Status                                                                                                            |
|-------------|-------------------------------------------------------------------------------------------------------------------------|
| Master      | ![Java CI with Gradle](https://github.com/DerCoderLukas/YourTeam/workflows/Java%20CI%20with%20Gradle/badge.svg) |

## Requirements

• Java 14

## Usage

As already mentioned, the project has a rest interface that manages the main access of the team. To put it into operation, download the deploy part from the release area and run the jar. Then some files are created that save the team status. From now on the rest api is ready for use and can be used without any problems with the created standard user, whose name and password can be found under "./users/users.yml".

## Rest interface

We decided to use a residual interface because it offers our customers great freedom in many decisions. So you can use our simple structured administrative tool, or you can choose to build your own web interface.
All this with the freedom to decide for yourself which programming language you want to use to access the interface.

### Safety

Of course, we are aware that all data that is fed into such a system is highly confidential. It is not only because of this that the entire software is open source. It is not without reason that one says: "Trust is good, control is better". To ensure security when accessing our system, we work with the json web token tool. This means that not all data has to be stored on the server, and you can still be sure that it is true. You can find more detailed information on [this website](https://jwt.io/).

## Administration

The administrative tool has not yet been developed at this point, but will be submitted as soon as possible. It will represent the management of the team in a console application and managed with primitive means.

## Web interface

If someone is involved in web development and is interested in developing an interface for our application, [you can contact me at any time](https://www.youtube.com/watch?v=nnNsGrvrqT4) by email (lukas.DerCoder@gmail.com). I will then upload them to github and of course mention the developer. I will probably not develop an interface because I am not satisfied with my code in this area.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

[MIT](https://choosealicense.com/licenses/mit/)
