# ITIS Courses API

## Setup

1. Using default user "postgres" create database with name "itis_service"
2. Change user password in application.properties if needed
3. Launch application

Tables will generate automatically

## Profiles

Is used two profiles: **dev** and **production**

**dev** profile is used on local, and using the local database. Students and Groups parse from kpfu.ru automatically <br>
**production** profile is used on remote, and using the remote database. Students and Groups create as tests data

In branch **heroku/master** you should always using the **production** profile!
