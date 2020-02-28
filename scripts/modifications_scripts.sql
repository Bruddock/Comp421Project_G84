
/*Complete payment release for pending invoices to Company7 */
UPDATE invoice
SET status = 'accepted'
WHERE status = 'pending' AND suppliername = 'Company17';

/*Accept payment from pending customer and update account balances */
UPDATE accountreceivable
    SET revenue = revenue + invoice.amount
FROM invoice
WHERE accountreceivable.acctid = invoice.acctid AND status = 'pending' AND clientemail is not null ;
/* Then update all pending invoices to accepted */
UPDATE invoice
SET status = 'accepted'
WHERE status = 'pending' AND clientemail is not null ;

/*Change menu 840 to vegan */
DELETE FROM contains WHERE menuid = 840;
INSERT INTO contains values (840, 'dish13');


