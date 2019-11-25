insert into users values ('simarsi');
insert into users values ('nickaggarwal');
insert into users values ('akmi');
insert into users values ('gopi');

insert into user_follower (username, follower_name) values ('simarsi', 'akmi');
insert into user_follower (username, follower_name) values ('simarsi', 'gopi');
insert into user_follower (username, follower_name) values ('nickaggarwal', 'akmi');
insert into user_follower (username, follower_name) values ('gopi', 'nickaggarwal');
insert into user_follower (username, follower_name) values ('akmi', 'simarsi');

insert into users_following (username, following_name) values ('akmi', 'simarsi');
insert into users_following (username, following_name) values ('gopi', 'simarsi');
insert into users_following (username, following_name) values ('akmi', 'nickaggarwal');
insert into users_following (username, following_name) values ('nickaggarwal', 'gopi');
insert into users_following (username, following_name) values ('simarsi', 'akmi');

insert into posts (post_id, upload_url, description, upvotes, username) values (1, 'https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcS42VxMCOJNsVew-Zw3x1mU9UrC62bw2cdra3JLrjESAVHJs11-', 'Cheers to an amazing night', 0, 'simarsi');
insert into posts (post_id, upload_url, description, upvotes, username) values (2, 'https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSq6RRbVcbZm7x6xuZsml3oJvn-9j9nIyx65KFpI-Kz8zfuQogn', 'My first marathon', 0, 'simarsi');
insert into posts (post_id, upload_url, description, upvotes, username) values (3, 'https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSYVAUg-XWmT6rg9rPwe4eHnQIF7rzdCMjpTHCx5AZV4el7QA1I', 'Met this guy on the trip, what a host.', 0, 'gopi');
insert into posts (post_id, upload_url, description, upvotes, username) values (4, 'https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcT5nTzpcGyS_67KRgjQUN2ipCSnjdiggjEKGu8m0bnYauPNPdKF', 'Human vs AI. The war begins!!', 0, 'akmi');
insert into posts (post_id, upload_url, description, upvotes, username) values (5, 'https://image.insider.com/5acfbea8facba86e1b8b4576?width=600&format=jpeg&auto=webp', 'Waddup Hooman', 0, 'nickaggarwal');

