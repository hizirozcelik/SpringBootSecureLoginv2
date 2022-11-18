INSERT INTO sec_user (name, lastName, email, encryptedPassword, enabled)
VALUES ('Simon', 'Hood', 'simon.hood@sheridancollege.ca', '$2a$10$1ltibqiyyBJMJQ4hqM7f0OusP6np/IHshkYc4TjedwHnwwNChQZCy', 1);

INSERT INTO sec_role (roleName)
VALUES ('ROLE_USER');
 
INSERT INTO user_role (userId, roleId)
VALUES (1, 1);
