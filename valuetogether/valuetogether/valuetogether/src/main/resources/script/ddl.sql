CREATE TABLE MEMBER (
    member_id BIGINT NOT NULL AUTO_INCREMENT,
    member_email VARCHAR(45) NOT NULL,
    member_pw VARCHAR(45) NOT NULL,
    member_name VARCHAR(45) NOT NULL,
    member_phone VARCHAR(45) NOT NULL,
    member_address VARCHAR(45) NOT NULL,
    member_gender VARCHAR(45) NOT NULL,
    member_nickname VARCHAR(45) NOT NULL,
    member_birthday DATE,
    PRIMARY KEY (member_id)
);

CREATE TABLE COMMENT (
     comment_id BIGINT NOT NULL AUTO_INCREMENT,
     content VARCHAR(255) NOT NULL,
     date DATE NOT NULL,
     likes BIGINT NOT NULL,
     member_id BIGINT NOT NULL,
     post_id BIGINT NOT NULL,
     PRIMARY KEY (comment_id),
     FOREIGN KEY (member_id) REFERENCES MEMBER(member_id),
     FOREIGN KEY (post_id) REFERENCES POST(post_id)
);

CREATE TABLE POST (
    post_id BIGINT NOT NULL AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    title VARCHAR(45) NOT NULL,
    subtitle VARCHAR(255) NOT NULL,
    article VARCHAR(255) NOT NULL,
    image VARCHAR(255) NULL,
    target_amount BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    is_confirm BOOLEAN NOT NULL,
    PRIMARY KEY (post_id),
    FOREIGN KEY (member_id) REFERENCES MEMBER(member_id)
)