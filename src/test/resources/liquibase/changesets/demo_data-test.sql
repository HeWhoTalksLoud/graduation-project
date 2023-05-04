-- pwd
insert into users (email, first_name, last_name, phone, role, password)
values ('pavel@mail.ru', 'Ludvig', 'Huyhton', '+4 (937) 938-1368', 1, '$2a$12$8.dOjLvHyfHmmUSKPZtVZ.FGIoYDlsyYpb8t/6ObIZSZWy.k67wWC'),
       ('bmerwe1@hibu.com', 'Bernard', 'Merwe', '+3 (579) 239-6132', 1, '$2a$12$8.dOjLvHyfHmmUSKPZtVZ.FGIoYDlsyYpb8t/6ObIZSZWy.k67wWC'),
       ('ljenno2@statcounter.com', 'Lucky', 'Jenno', '+8 (665) 552-9583', 0, '$2a$12$8.dOjLvHyfHmmUSKPZtVZ.FGIoYDlsyYpb8t/6ObIZSZWy.k67wWC'),
       ('kbierton3@furl.net', 'Katey', 'Bierton', '+8 (765) 915-1803', 0, '$2a$12$8.dOjLvHyfHmmUSKPZtVZ.FGIoYDlsyYpb8t/6ObIZSZWy.k67wWC'),
       ('ajohansson4@deviantart.com', 'Anatole', 'Johansson', '+4 (422) 642-8869', 0, '$2a$12$8.dOjLvHyfHmmUSKPZtVZ.FGIoYDlsyYpb8t/6ObIZSZWy.k67wWC');



insert into ads (author_id, publish_date_time, title, description, price)
values (2, '2022-06-19 19:19:20', 'Camel, dromedary', 'imperdiet sapien urna pretium nisl ut volutpat sapien arcu sed augue aliquam erat volutpat in congue etiam justo', 7076),
      (2, '2022-07-20 15:34:56', 'Indian red admiral', 'eu magna vulputate luctus cum sociis natoque penatibus et magnis dis parturient montes nascetur ridiculus mus vivamus vestibulum sagittis sapien cum sociis natoque penatibus et magnis dis parturient montes nascetur', 14950),
      (4, '2022-04-20 12:12:33', 'Vulture, bengal', 'nisi eu orci mauris lacinia sapien quis libero nullam sit amet turpis elementum ligula vehicula consequat morbi a ipsum integer a nibh in quis justo maecenas rhoncus aliquam', 8510),
      (4, '2023-02-13 14:47:25', 'Chipmunk, least', 'semper est quam pharetra magna ac consequat metus sapien ut nunc vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae mauris viverra diam', 3245),
      (5, '2022-04-09 23:38:26', 'Bee-eater, nubian', 'penatibus et magnis dis parturient montes nascetur ridiculus mus etiam vel augue vestibulum rutrum rutrum neque aenean auctor gravida', 19601),
      (5, '2023-02-15 10:50:19', 'Starfish, crown of thorns', 'nibh in lectus pellentesque at nulla suspendisse potenti cras in purus eu magna vulputate luctus cum sociis natoque penatibus et magnis dis parturient montes nascetur ridiculus mus vivamus vestibulum sagittis', 14848),
      (4, '2022-05-02 02:36:57', 'Black curlew', 'platea dictumst aliquam augue quam sollicitudin vitae consectetuer eget rutrum at lorem integer', 2951),
      (5, '2022-08-30 16:49:15', 'Tree porcupine', 'rhoncus aliquam lacus morbi quis tortor id nulla ultrices aliquet', 13519),
      (2, '2022-05-11 19:57:53', 'Hoary marmot', 'sem sed sagittis nam congue risus semper porta volutpat quam pede lobortis ligula sit amet eleifend pede', 11609),
      (2, '2023-03-16 16:12:41', 'Dove, emerald-spotted wood', 'nullam molestie nibh in lectus pellentesque at nulla suspendisse potenti cras in purus eu magna vulputate luctus cum sociis natoque', 18199),
      (1, '2022-06-10 02:27:17', 'Yellow baboon', 'potenti in eleifend quam a odio in hac habitasse platea dictumst maecenas', 7920),
      (3, '2022-07-28 05:45:52', 'Snowy owl', 'ante vel ipsum praesent blandit lacinia erat vestibulum sed magna at nunc commodo placerat praesent blandit nam nulla integer pede justo lacinia', 18479),
      (4, '2022-08-18 05:28:49', 'Goliath heron', 'lectus aliquam sit amet diam in magna bibendum imperdiet nullam orci pede venenatis non sodales sed tincidunt eu felis fusce posuere felis sed lacus morbi sem mauris laoreet ut', 10415),
      (5, '2022-05-20 23:12:29', 'Boar, wild', 'leo rhoncus sed vestibulum sit amet cursus id turpis integer aliquet', 11712),
      (3, '2022-09-16 03:15:05', 'Ocelot', 'ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae duis faucibus accumsan odio curabitur convallis duis consequat dui', 7402);

insert into comments (author_id, ad_id, creation_date_time, text) values (5, 6, '2023-02-17 02:20:45', 'in est risus auctor sed tristique in tempus sit amet sem fusce consequat nulla nisl nunc nisl');
insert into comments (author_id, ad_id, creation_date_time, text) values (3, 9, '2022-05-11 00:27:11', 'convallis tortor risus dapibus augue vel accumsan tellus nisi eu orci mauris lacinia sapien quis libero nullam sit amet turpis elementum ligula vehicula consequat');
insert into comments (author_id, ad_id, creation_date_time, text) values (5, 2, '2023-01-10 02:51:07', 'curae nulla dapibus dolor vel est donec odio justo sollicitudin ut suscipit a feugiat et eros vestibulum ac est lacinia nisi venenatis');
insert into comments (author_id, ad_id, creation_date_time, text) values (3, 3, '2022-09-17 22:46:27', 'amet eleifend pede libero quis orci nullam molestie');
insert into comments (author_id, ad_id, creation_date_time, text) values (4, 6, '2022-12-03 01:54:58', 'montes nascetur ridiculus mus vivamus vestibulum sagittis sapien cum sociis natoque penatibus et magnis dis parturient montes nascetur');
insert into comments (author_id, ad_id, creation_date_time, text) values (1, 4, '2023-03-26 08:00:33', 'vulputate ut ultrices vel augue vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae donec pharetra magna vestibulum aliquet ultrices erat tortor sollicitudin mi sit amet');
insert into comments (author_id, ad_id, creation_date_time, text) values (4, 2, '2022-06-27 15:08:12', 'pretium nisl ut volutpat sapien arcu sed augue aliquam erat volutpat in congue');
insert into comments (author_id, ad_id, creation_date_time, text) values (4, 6, '2022-04-02 21:42:35', 'consectetuer adipiscing elit proin risus praesent lectus vestibulum quam sapien varius ut blandit non interdum in ante vestibulum');
insert into comments (author_id, ad_id, creation_date_time, text) values (2, 7, '2022-11-19 07:19:00', 'turpis sed ante vivamus tortor duis mattis egestas metus aenean fermentum donec ut mauris');
insert into comments (author_id, ad_id, creation_date_time, text) values (2, 13, '2022-04-09 18:31:15', 'id luctus nec molestie sed justo pellentesque viverra pede ac diam cras pellentesque volutpat dui maecenas tristique est et tempus semper');
insert into comments (author_id, ad_id, creation_date_time, text) values (5, 12, '2022-10-27 03:30:03', 'felis donec semper sapien a libero nam dui proin leo odio porttitor id consequat in consequat ut nulla sed accumsan felis ut');
insert into comments (author_id, ad_id, creation_date_time, text) values (2, 1, '2022-08-15 01:30:05', 'morbi a ipsum integer a nibh in quis justo maecenas');
insert into comments (author_id, ad_id, creation_date_time, text) values (3, 2, '2022-06-07 16:14:22', 'consequat ut nulla sed accumsan felis ut at dolor quis odio consequat varius integer ac leo pellentesque ultrices mattis odio donec vitae nisi nam ultrices libero');
insert into comments (author_id, ad_id, creation_date_time, text) values (1, 13, '2023-03-04 22:32:58', 'justo sit amet sapien dignissim vestibulum vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae nulla dapibus dolor');
insert into comments (author_id, ad_id, creation_date_time, text) values (2, 2, '2022-07-13 10:21:38', 'adipiscing elit proin interdum mauris');
insert into comments (author_id, ad_id, creation_date_time, text) values (3, 14, '2022-07-13 07:45:03', 'convallis nunc proin at turpis a pede posuere nonummy integer non velit donec diam neque vestibulum eget vulputate ut ultrices vel augue vestibulum ante ipsum primis in');
insert into comments (author_id, ad_id, creation_date_time, text) values (1, 6, '2022-08-02 16:46:54', 'amet nunc viverra dapibus nulla suscipit ligula in lacus curabitur at ipsum ac tellus semper interdum mauris ullamcorper');
insert into comments (author_id, ad_id, creation_date_time, text) values (5, 6, '2022-09-08 07:16:53', 'eros suspendisse accumsan tortor quis turpis sed ante vivamus tortor duis mattis egestas metus aenean');
insert into comments (author_id, ad_id, creation_date_time, text) values (5, 4, '2022-12-13 05:43:50', 'penatibus et magnis dis parturient montes nascetur ridiculus mus vivamus vestibulum sagittis sapien cum sociis natoque penatibus et magnis dis parturient');
insert into comments (author_id, ad_id, creation_date_time, text) values (1, 3, '2022-08-03 09:34:35', 'mollis molestie lorem quisque ut erat');
insert into comments (author_id, ad_id, creation_date_time, text) values (1, 4, '2022-06-02 00:31:24', 'mauris morbi non lectus aliquam sit amet diam in magna bibendum');
insert into comments (author_id, ad_id, creation_date_time, text) values (1, 7, '2023-03-26 02:56:26', 'habitasse platea dictumst etiam faucibus cursus urna ut tellus nulla ut erat id mauris vulputate elementum nullam varius nulla facilisi cras non velit nec nisi vulputate nonummy maecenas tincidunt lacus');
insert into comments (author_id, ad_id, creation_date_time, text) values (2, 12, '2022-11-11 16:37:24', 'porta volutpat erat quisque erat eros viverra eget congue eget semper rutrum nulla nunc purus phasellus in felis donec semper sapien a');
insert into comments (author_id, ad_id, creation_date_time, text) values (1, 15, '2022-04-24 19:54:53', 'pede justo eu massa donec dapibus duis at velit eu');
insert into comments (author_id, ad_id, creation_date_time, text) values (5, 6, '2023-04-06 04:49:30', 'enim in tempor turpis nec euismod scelerisque quam turpis adipiscing lorem vitae mattis nibh ligula nec sem duis aliquam convallis nunc proin at turpis a pede posuere');
insert into comments (author_id, ad_id, creation_date_time, text) values (5, 3, '2022-08-19 19:57:07', 'varius integer ac leo pellentesque ultrices mattis odio');
insert into comments (author_id, ad_id, creation_date_time, text) values (2, 1, '2022-10-27 23:15:28', 'vitae mattis nibh ligula nec sem duis aliquam convallis nunc proin at turpis a pede');
insert into comments (author_id, ad_id, creation_date_time, text) values (4, 3, '2022-11-16 16:27:08', 'non quam nec dui luctus rutrum nulla tellus in sagittis dui');
insert into comments (author_id, ad_id, creation_date_time, text) values (3, 15, '2023-01-03 03:39:56', 'interdum mauris ullamcorper purus sit amet nulla quisque arcu libero rutrum ac lobortis vel dapibus at diam nam tristique');
insert into comments (author_id, ad_id, creation_date_time, text) values (4, 6, '2022-12-23 06:10:24', 'volutpat in congue etiam justo etiam pretium iaculis justo in hac habitasse platea dictumst etiam faucibus cursus urna ut tellus nulla ut erat id mauris');
insert into comments (author_id, ad_id, creation_date_time, text) values (1, 8, '2022-11-26 06:17:48', 'tellus in sagittis dui vel nisl duis ac nibh fusce lacus purus aliquet at');
insert into comments (author_id, ad_id, creation_date_time, text) values (3, 14, '2023-02-20 15:18:46', 'sed augue aliquam erat volutpat in congue etiam justo etiam pretium iaculis justo in hac');
insert into comments (author_id, ad_id, creation_date_time, text) values (2, 1, '2022-10-20 12:44:54', 'potenti cras in purus eu magna vulputate luctus cum sociis natoque penatibus et magnis dis parturient montes nascetur ridiculus mus vivamus vestibulum sagittis sapien cum sociis');
insert into comments (author_id, ad_id, creation_date_time, text) values (5, 5, '2022-08-11 03:28:13', 'at nulla suspendisse potenti cras');
insert into comments (author_id, ad_id, creation_date_time, text) values (2, 15, '2023-03-14 05:22:47', 'sed augue aliquam erat volutpat in congue etiam justo etiam pretium iaculis justo in hac habitasse platea dictumst etiam faucibus cursus urna');
insert into comments (author_id, ad_id, creation_date_time, text) values (5, 9, '2022-10-28 06:54:57', 'fusce consequat nulla nisl nunc nisl duis bibendum felis sed interdum venenatis turpis enim blandit mi in porttitor pede justo eu');
insert into comments (author_id, ad_id, creation_date_time, text) values (3, 3, '2022-07-23 21:42:41', 'nisi venenatis tristique fusce congue diam id ornare imperdiet sapien urna pretium nisl ut volutpat sapien arcu sed augue aliquam erat volutpat in');
insert into comments (author_id, ad_id, creation_date_time, text) values (5, 1, '2022-10-19 02:24:36', 'est lacinia nisi venenatis tristique fusce congue diam');
insert into comments (author_id, ad_id, creation_date_time, text) values (3, 8, '2023-01-21 19:54:42', 'donec dapibus duis at velit eu est congue elementum in hac habitasse platea dictumst morbi vestibulum velit id pretium iaculis diam erat fermentum justo nec condimentum neque sapien placerat');
insert into comments (author_id, ad_id, creation_date_time, text) values (1, 7, '2022-09-03 13:36:01', 'ultrices posuere cubilia curae donec pharetra magna vestibulum aliquet ultrices erat tortor sollicitudin mi sit amet lobortis sapien sapien non mi integer ac neque duis bibendum morbi non quam nec');
insert into comments (author_id, ad_id, creation_date_time, text) values (5, 4, '2022-08-11 00:50:06', 'sed tincidunt eu felis fusce posuere felis sed lacus morbi sem mauris');
insert into comments (author_id, ad_id, creation_date_time, text) values (5, 5, '2022-12-26 19:20:09', 'nulla ultrices aliquet maecenas leo odio condimentum id luctus nec molestie sed justo pellentesque');
insert into comments (author_id, ad_id, creation_date_time, text) values (3, 3, '2023-04-14 03:38:30', 'sapien iaculis congue vivamus metus arcu adipiscing');
insert into comments (author_id, ad_id, creation_date_time, text) values (5, 10, '2022-04-30 13:55:36', 'ac tellus semper interdum mauris ullamcorper purus sit amet');
insert into comments (author_id, ad_id, creation_date_time, text) values (5, 1, '2023-03-22 14:17:26', 'in eleifend quam a odio in hac habitasse platea dictumst maecenas ut massa quis augue luctus tincidunt nulla');
