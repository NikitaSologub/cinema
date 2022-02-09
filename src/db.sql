CREATE TABLE public.film
(
    id            int       NOT NULL GENERATED ALWAYS AS IDENTITY,
    film_title    varchar   NOT NULL,
    film_date     timestamp NOT NULL,
    ticket_amount int       NOT NULL
);

CREATE TABLE public.ticket
(
    id            int NOT NULL GENERATED ALWAYS AS IDENTITY,
    film_id       int NOT NULL,
    ticket_price  int NOT NULL,
    place_number  int NOT NULL,
    ticket_amount int NOT NULL
);

CREATE TABLE public."user"
(
    id            int     NOT NULL GENERATED ALWAYS AS IDENTITY,
    user_login    varchar NOT NULL,
    user_password varchar NULL,
    user_access   varchar NOT NULL
);