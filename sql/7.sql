SELECT u.name, GROUP_CONCAT(u.name SEPARATOR ',') FROM USER u GROUP BY u.name;