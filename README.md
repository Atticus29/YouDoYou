# _YouDoYou_

#### _YouDoYou, 04-10-2017_

#### By _**asdf and asdf**_

## Description
_Example text for the description of the project_


## Specifications

| Behavior                   | Input Example     | Output Example    |
| -------------------------- | -----------------:| -----------------:|



## Setup/Installation Requirements

* _Clone the repository_
* _Run the command 'gradle run'_
* _Open browser and go to localhost:4567_

CREATE DATABASE you_do_you;
CREATE TABLE tasks (id serial PRIMARY KEY, name varchar, created timestamp, due timestamp, difficulty int, estimated_time int, importance int, completed boolean, priority_level int, skill_id int, task_list_id int, user_id int);
CREATE TABLE task_lists (id serial PRIMARY KEY, name varchar, priority_level int, number_tasks int, created timestamp, due timestamp, completed boolean, skill_id int, user_id int);
CREATE TABLE skills (id serial PRIMARY KEY, name varchar, level int, experience int, created timestamp, user_id int);
CREATE TABLE users (id serial PRIMARY KEY, name varchar, level int, experience int, created timestamp);
CREATE TABLE avatars (id serial PRIMARY KEY, user_id int);
CREATE TABLE analytics (id serial PRIMARY KEY, user_id int);

* test database:
CREATE DATABASE you_do_you_test WITH TEMPLATE you_do_you;

DROP DATABASE you_do_you_test;

### License

Copyright (c) 2017 **_asdf and asdf_**

This software is licensed under the MIT license.
