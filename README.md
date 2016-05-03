# sic

__sic__ is a web application built to manage quotes attributed to people. It is based on 
[Tingo](https://github.com/xxyy/tingo), which uses technologies like AngularJS (JS is bad, I know, but this is supposed
to be a simple and fast project), lesscss and the Spring Framework.

## What is it?

Specifically, _sic_ allows anyone knowing a secret code to register, view and manage quotes. People do currently have
to be created by an administrator, but this might change in the future. It will also provide a convenient print view
which allows people to print quotes for displaying them on a wall or something.

## Installation

To install _sic_, you first need to build it: (assuming you're on GNU/Linux, other platforms work similarly)

````bash
git clone https://github.com/xxyy/sic.git
cd sic
./gradlew build
cp application.yml-default application.yml
````

Then, you need to edit the `application.yml` config file with your favourite editor and insert database credentials.
sic was built for use with MySQL or MariaDB. You do not need to perform any database setup except for creating the
database you named in your config file. (`sic` by default)

You can then run the application either using Gradle directly:

````bash
./gradlew bootRun
````

or by running the `.jar` file created in the `build/libs/` directory.

It will start on port 8080 by default.

## License

This project is licensed under the Apace License, Version 2.0. You can find a copy in the `LICENSE` file.

## Support

Please, just open an issue at [GitHub](https://github.com/xxyy/sic/issues). Pull Requests are obviously welcome. 
