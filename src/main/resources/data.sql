insert into customer (id,idate, name,surname,identity_number)
values (1,NOW(),'Oguzhan','İnce',33296117800);

insert into currency(id,idate,code)
values (1,NOW(),'TRY'),
         (2,NOW(),'USD'),
         (3,NOW(),'EUR');

insert into wallet (id,idate,wallet_name,customer_id,currency_id,balance,usable_balance)
values (1,NOW(),'Oguzhan İnce TRY Cüzdan',1,1,0,0);