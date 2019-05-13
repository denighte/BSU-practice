SELECT u.name, p.creation_date, p.description FROM USER u INNER JOIN PHOTO_POST p ON p.user_id = u.id WHERE u.id = 1
 ORDER BY p.creation_date ASC;