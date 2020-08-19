INSERT INTO public.product_types (id, product_type) VALUES (1, 'Clothes');
INSERT INTO public.product_types (id, product_type) VALUES (2, 'Device');
INSERT INTO public.product_types (id, product_type) VALUES (3, 'Food');
INSERT INTO public.product_types (id, product_type) VALUES (4, 'Sweets');

INSERT INTO public.orders (id, user_id, transaction_id, status_id, is_processed) VALUES (1, 1, 1, 1, true);
INSERT INTO public.orders (id, user_id, transaction_id, status_id, is_processed) VALUES (2, 1, 2, 1, true);

INSERT INTO public.products (id, quantity, cost, product_type_id, metric_id, name) VALUES (1, 1, 1, 1, 1, 'Prod1');
INSERT INTO public.products (id, quantity, cost, product_type_id, metric_id, name) VALUES (2, 1, 3, 3, 3, 'Prod2');
INSERT INTO public.products (id, quantity, cost, product_type_id, metric_id, name) VALUES (3, 1, 2, 4, 2, 'Prod3');

INSERT INTO public.shops (id, shop_name, address) VALUES (1, 'Shop1', 'SomeAddress');

INSERT INTO public.transaction_products (transaction_id, trans_prod_info_id) VALUES (1, 1);
INSERT INTO public.transaction_products (transaction_id, trans_prod_info_id) VALUES (1, 2);
INSERT INTO public.transaction_products (transaction_id, trans_prod_info_id) VALUES (2, 3);
INSERT INTO public.transaction_products (transaction_id, trans_prod_info_id) VALUES (2, 4);

INSERT INTO public.transaction_products_info (id, product_id, quantity, shop_id, is_at_storage, transaction_id, current_cost) VALUES (2, 1, 1, 1, false, 1, 2);
INSERT INTO public.transaction_products_info (id, product_id, quantity, shop_id, is_at_storage, transaction_id, current_cost) VALUES (1, 1, 1, 1, false, 1, 1);
INSERT INTO public.transaction_products_info (id, product_id, quantity, shop_id, is_at_storage, transaction_id, current_cost) VALUES (3, 3, 1, 1, false, 2, 2);
INSERT INTO public.transaction_products_info (id, product_id, quantity, shop_id, is_at_storage, transaction_id, current_cost) VALUES (4, 2, 2, 1, false, 2, 1);

INSERT INTO public.transactions (id, timestamp, cost_sum) VALUES (1, '2020-08-15 17:41:43.000000', 2);
INSERT INTO public.transactions (id, timestamp, cost_sum) VALUES (2, '2020-08-19 17:41:43.000000', 4);

//Password admin
INSERT INTO public.users (id, username, password_hash, balance, role) VALUES (1, 'admin', '$2a$10$kfYvG5ubMbTU1ZyMbHtk2eh/gY9OHmxJS8OUWFi4SXqRvKzQ6/uNK', 10000000, 'ROLE_ADMIN');
INSERT INTO public.users (id, username, password_hash, balance, role) VALUES (2, 'user', '$2a$10$j500kCpUCOAiVEKHLNSAaOt9WN9DIRKo3AaJgWHsPpvpbtUmUcLLS', 0, 'ROLE_USER');

INSERT INTO public.metric_types (id, metric_type) VALUES (1, 'pack');
INSERT INTO public.metric_types (id, metric_type) VALUES (2, 'kg');
INSERT INTO public.metric_types (id, metric_type) VALUES (3, 'l');

INSERT INTO public.order_statuses (id, order_status) VALUES (1, 'Created');
INSERT INTO public.order_statuses (id, order_status) VALUES (2, 'Payed');
INSERT INTO public.order_statuses (id, order_status) VALUES (3, 'Finished');