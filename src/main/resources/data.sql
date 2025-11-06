insert into customer (id,idate, name,surname,identity_number)
values (1,NOW(),'Oguzhan','İnce',33296117800);

insert into currency(id,idate,code)
values (1,NOW(),'TRY'),
         (2,NOW(),'USD'),
         (3,NOW(),'EUR');

insert into users(id,idate,username,password,role,customer_id)
values (1,NOW(),'test','$2a$10$oxjngVRyp.nq66iZE6kWduc8S4J9n/WxRgva6dtiuI1hbiSeoMcr.','CUSTOMER',1),
         (2,NOW(),'admin','$2a$10$S7bWQRNS4TLwa4.Zd7c0c.CWm5naCWI370Td2gI7WdNGnr.numbJ.','ADMIN',null);

