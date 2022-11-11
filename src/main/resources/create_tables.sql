CREATE TABLE public.user (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(100) NOT NULL UNIQUE,
    password varchar(100) NOT NULL,
    created_at timestamp with time zone);
CREATE TABLE post (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    text text NOT NULL,
    created_at timestamp with time zone,
    user_id int REFERENCES public.user(id) NOT NULL);
CREATE TABLE comment (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    text text,
    post_id int REFERENCES post(id) NOT NULL,
    user_id int REFERENCES public.user(id) NOT NULL,
    created_at timestamp with time zone);
CREATE TABLE public.like (
    id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id int REFERENCES public.user(id) NOT NULL,
    post_id int REFERENCES post(id),
    comment_id int REFERENCES comment(id) CHECK (post_id IS NULL OR comment_id IS NULL));