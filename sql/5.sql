SELECT u.id, u.name, u.password_hash 
FROM USER u INNER JOIN PHOTO_POST p ON p.user_id = u.id
 GROUP BY u.name
 HAVING count(p.id) >= 3;