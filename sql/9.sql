SELECT u.name, p.description, p.creation_date, p.photo_link 
FROM USER u INNER JOIN PHOTO_POST p ON p.user_id = u.id WHERE LENGTH(p.description) > 20;