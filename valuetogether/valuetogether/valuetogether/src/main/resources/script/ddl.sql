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
    content VARCHAR(255) NOT NULL,
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
INSERT INTO tag (tag_name) VALUES ('모두의교육'), ('기본생활지원'), ('안정된일자리'), ('건강한삶'), ('인권평화와역사'), ('동물'), ('지역공동체'), ('더나은사회'), ('환경'), ('아동|청소년'), ('청년'), ('여성'), ('실버세대'), ('장애인'), ('이주민|다문화'), ('지구촌'), ('어려운이웃'), ('우리사회'), ('유기동물'), ('야생동물')