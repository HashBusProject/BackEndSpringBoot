CREATE TABLE roles
(
    rule_type_ID          int AUTO_INCREMENT PRIMARY KEY not null,
    rule_type_description varchar(20)
);

CREATE TABLE users
(
    user_ID      INT AUTO_INCREMENT PRIMARY KEY,
    rule_type_ID INT          NOT NULL,
    username     VARCHAR(50)  NOT NULL,
    name         VARCHAR(50)  NOT NULL,
    password     VARCHAR(25)  NOT NULL,
    email        VARCHAR(100) NOT NULL,
    foreign key (rule_type_ID) references roles (rule_type_ID)
);

CREATE TABLE points
(
    point_ID   INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    point_name varchar(50)        NOT NULL,
    x_point    FLOAT(6, 6),
    y_point    FLOAT(6, 6)
);
CREATE TABLE journeys
(
    journey_ID           INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    source_point_ID      INT,
    destination_point_ID INT,
    journey_name         VARCHAR(50),
    foreign key (source_point_ID) references points (point_id),
    foreign key (destination_point_ID) references points (point_id)
);
CREATE TABLE tickets
(
    ticket_ID  INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    price      DECIMAL(4, 2),
    journey_ID INT not null ,
    FOREIGN KEY (journey_ID) REFERENCES journeys (journey_ID)
);

CREATE TABLE stop_points_for_journey
(
    journey_ID INT,
    point_ID   INT,
    PRIMARY KEY (journey_ID, point_ID),
    FOREIGN KEY (journey_ID) REFERENCES journeys (journey_ID),
    FOREIGN KEY (point_ID) REFERENCES points (point_ID)
);

-- create bus table
CREATE TABLE buses
(
    bus_ID    INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    driver_ID INT,
    working   BOOLEAN,
    FOREIGN KEY (driver_ID) REFERENCES users (user_ID)
);


-- create schedule table
CREATE TABLE schedules
(
    bus_ID     INT,
    journey_ID INT,
    time       TIME,
    PRIMARY KEY (journey_ID, bus_ID, time),
    FOREIGN KEY (bus_ID) REFERENCES buses (bus_ID),
    FOREIGN KEY (journey_ID) REFERENCES journeys (journey_ID)
);

-- inserting data --

-- Insert demo data into roles table
INSERT INTO roles (rule_type_description)
VALUES ('Student'),
       ('Driver'),
       ('Organizer'),
       ('Admin');

-- Insert demo data into users table
INSERT INTO users (rule_type_ID, username, name, password, email)
VALUES (1, 'student1', 'John Doe', 'password1', 'student1@example.com'),
       (2, 'driver1', 'Alice Smith', 'password2', 'driver1@example.com'),
       (3, 'organizer1', 'Bob Johnson', 'password3', 'organizer1@example.com'),
       (4, 'admin1', 'Admin User', 'adminpass', 'admin@example.com'),
       (1, 'student2', 'Jane Doe', 'password4', 'student2@example.com'),
       (2, 'driver2', 'Charlie Brown', 'password5', 'driver2@example.com'),
       (3, 'organizer2', 'Eve Williams', 'password6', 'organizer2@example.com'),
       (4, 'admin2', 'Super Admin', 'adminpass2', 'admin2@example.com'),
       (1, 'student3', 'Sam Johnson', 'password7', 'student3@example.com'),
       (2, 'driver3', 'Dave Wilson', 'password8', 'driver3@example.com');

-- Insert demo data into points table
INSERT INTO points (point_name, x_point, y_point)
VALUES ('Point A', 40.712801, -74.006001),
       ('Point B', 34.052201, -118.243701),
       ('Point C', 41.878101, -87.629801),
       ('Point D', 51.507401, -0.127801),
       ('Point E', 37.774901, -122.419401),
       ('Point F', 32.715701, -117.161101),
       ('Point G', 45.421501, -75.699301),
       ('Point H', 35.689501, 139.691701),
       ('Point I', 55.755801, 37.617601),
       ('Point J', -33.868801, 151.209301);

-- Insert demo data into journeys table
INSERT INTO journeys (source_point_ID, destination_point_ID, journey_name)
VALUES (1, 3, 'Journey 1'),
       (2, 5, 'Journey 2'),
       (6, 10, 'Journey 3');

-- Insert demo data into tickets table
INSERT INTO tickets (price, journey_ID)
VALUES (25.50, 1),
       (30.75, 2),
       (20.00, 3);

-- Insert demo data into stop_points_for_journey table
INSERT INTO stop_points_for_journey (journey_ID, point_ID)
VALUES (1, 2),
       (2, 3),
       (2, 4),
       (3, 7),
       (3, 8),
       (3, 9);

-- Insert demo data into buses table
INSERT INTO buses (driver_ID, working)
VALUES (2, TRUE),
       (6, TRUE),
       (10, FALSE);

-- Insert demo data into schedules table
INSERT INTO schedules (bus_ID, journey_ID, time)
VALUES (1, 1, '08:00:00'),
       (2, 2, '09:30:00'),
       (3, 3, '11:00:00'),
       (1, 1, '13:00:00'),
       (2, 2, '18:30:00'),
       (3, 3, '14:00:00');
