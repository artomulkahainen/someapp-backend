CREATE TABLE users (id serial PRIMARY KEY, username VARCHAR NOT NULL, password VARCHAR NOT NULL, created_at TIMESTAMP DEFAULT NOW());

CREATE TABLE photos (id serial PRIMARY KEY, image_url VARCHAR NOT NULL, user_id INT NOT NULL, created_at TIMESTAMP DEFAULT NOW(), FOREIGN KEY(user_id) REFERENCES users(id));

CREATE TABLE photoComments ( id serial PRIMARY KEY, comment_text VARCHAR NOT NULL, photo_id INT NOT NULL, user_id INT NOT NULL, created_at TIMESTAMP DEFAULT NOW(), FOREIGN KEY(photo_id) REFERENCES photos(id), FOREIGN KEY(user_id) REFERENCES users(id));

CREATE TABLE posts ( id serial PRIMARY KEY, post VARCHAR NOT NULL, user_id INT NOT NULL, created_at TIMESTAMP DEFAULT NOW(), FOREIGN KEY(user_id) REFERENCES users(id));

CREATE TABLE postComments ( id serial PRIMARY KEY, comment_text VARCHAR NOT NULL, post_id INT NOT NULL, user_id INT NOT NULL, created_at TIMESTAMP DEFAULT NOW(), FOREIGN KEY(post_id) REFERENCES posts(id), FOREIGN KEY(user_id) REFERENCES users(id));

CREATE TABLE photolikes ( user_id INT NOT NULL, photo_id INT NOT NULL, created_at TIMESTAMP DEFAULT NOW(), FOREIGN KEY(user_id) REFERENCES users(id), FOREIGN KEY(photo_id) REFERENCES photos(id), PRIMARY KEY(user_id, photo_id));

CREATE TABLE postlikes ( user_id INT NOT NULL, post_id INT NOT NULL, created_at TIMESTAMP DEFAULT NOW(), FOREIGN KEY(user_id) REFERENCES users(id), FOREIGN KEY(post_id) REFERENCES posts(id), PRIMARY KEY(user_id, post_id));

CREATE TABLE relationship ( user_id INT NOT NULL, user2_id INT NOT NULL, status INT NOT NULL, action_user_id INT NOT NULL, created_at TIMESTAMP DEFAULT NOW(), FOREIGN KEY(user_id) REFERENCES users(id), FOREIGN KEY(user2_id) REFERENCES users(id), PRIMARY KEY(user_id, user2_id));

##### RELATIONSHIP STATUS CODES: 0 Pending, 1 Accepted, 2 Declined, 3 Blocked
