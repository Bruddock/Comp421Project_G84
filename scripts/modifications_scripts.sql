
--Complete payment release for pending invoices to Company7 
UPDATE invoice
SET status = 'accepted'
WHERE status = 'pending' AND suppliername = 'Company17';

--Accept payment from pending customer and update account balances 
UPDATE accountreceivable
    SET revenue = revenue + invoice.amount
FROM invoice
WHERE accountreceivable.acctid = invoice.acctid AND status = 'pending' AND clientemail is not null ;
--Then update all pending invoices to accepted 
UPDATE invoice
SET status = 'accepted'
WHERE status = 'pending' AND clientemail is not null ;

--Change menu 840 to vegan 
DELETE FROM contains WHERE menuid = 840;
INSERT INTO contains values (840, 'dish13');

-- Select all duplicates of a name, ingredientname pairing
SELECT name, ingredientname
FROM
    (SELECT name, ingredientname,
            ROW_NUMBER() OVER( PARTITION BY name, ingredientname
                ORDER BY  ingredientname ) AS numrows
     FROM ingredients ) t
WHERE t.numrows > 1;

--Clone, delete duplicate name, ingredientname pairings and reinstate ingredients.
CREATE TABLE ingredients_temp (LIKE ingredients);

INSERT INTO ingredients_temp (companyname, name , ordered, received, quantity, ingredientname)
    SELECT DISTINCT ON (name, ingredientname) 
    companyname, name, ordered, received, quantity, ingredientname 
    FROM ingredients;
    
DROP TABLE ingredients;

ALTER TABLE ingredients_temp RENAME TO ingredients;

