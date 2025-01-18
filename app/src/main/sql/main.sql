CREATE DATABASE Tasker;

CREATE TABLE user (
    user_id INT PRIMARY KEY,
    name VARCHAR(128),
    auth VARCHAR(32),
    pass VARCHAR(256),
);

CREATE TABLE appointment (
    app_id VARCHAR(256),
    user_id INT,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    time TIMESTAMP,
    FOREIGN KEY(user_id) REFERENCES user(user_id),
);
