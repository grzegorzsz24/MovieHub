insert into
    users (email, password)
values
    ('admin@example.com', '$2y$10$H9B0umVBgUUE4AZVvzvomONcA.aXjo6ZaL7Wuvku719Nl8PGESsN6'),
    ('user@example.com', '$2y$10$H9B0umVBgUUE4AZVvzvomONcA.aXjo6ZaL7Wuvku719Nl8PGESsN6');

insert into
    user_role (name, description)
values
    ('ADMIN', 'pełne uprawnienia'),
    ('USER', 'podstawowe uprawnienia, możliwość oddawania głosów');

insert into
    user_roles (user_id, role_id)
values
    (1, 1),
    (2, 2);