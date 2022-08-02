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
    title VARCHAR(255) NOT NULL,
    subtitle VARCHAR(255) NOT NULL,
    article VARCHAR(255) NOT NULL,
    image VARCHAR(255) NULL,
    target_amount BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    is_confirm BOOLEAN NOT NULL,
    PRIMARY KEY (post_id),
    FOREIGN KEY (member_id) REFERENCES MEMBER(member_id)
);

CREATE TABLE TAG (
    tag_id BIGINT NOT NULL AUTO_INCREMENT,
    tag_name varchar(255) NOT NULL,
    PRIMARY KEY (tag_id)
);
INSERT INTO TAG (tag_name) VALUES ('어려운이웃'),('행복한노후'),('여성인권'),('심리상담'),('환경교육'),('학대아동지원'),('환경을위한실천'),('우크라이나긴급모금'),('세상을바꾸는여성'),('언택트프로젝트')