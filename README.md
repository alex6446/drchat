## drchat
### Requirements
* openjdk 15.0.2
* gradle 6.8.3
* mariadb 10.5.9
### How to run
1. Database
    1. Install and run MariaDB on localhost:3306
    1. Create DATABASE with utf8_unicode_ci
1. Server
    ```bash
    gradle run -PmainClass=drchat.server.Server
    ```
1. Client(s)
    ```bash
    gradle run
    ```
    or
    ```bash
    gradle run -PmainClass=drchat.client.App
    ```

