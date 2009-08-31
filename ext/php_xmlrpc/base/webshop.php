<?php
include 'dbconnector.php';
/**
 * alle eigenschaften eines webshopabgleichs
 */
class Webshop extends DbConnector{


	public function __construct(){

		}
	/**
	 * Gibt die neuen Kontakte im Array zurück
	 * @param $lastContactID
	 * @return Array
	 */
	public function getNewContacts($cID){
            $gesamteContacts = array();
		try{
			$pdo = new DbConnector();
			$newContacts = $pdo->prepare("SELECT * FROM customers_info LEFT JOIN
                            ( customers, address_book, countries ) ON ( customers_info.customers_info_id =
                              customers.customers_id AND customers.customers_id = address_book_id AND
                              address_book.entry_country_id = countries.countries_id ) WHERE
                              customers_info_id > ?");
			$newContacts->execute(array($cID));
			$tmp = $newContacts->fetchAll();

			foreach ($tmp as $row) {
			if($row['customers_gender'] == 'm') {
				$gender = true;
			}else $gender = false;

			if($row['customers_vat_id_status'] != null && $row['customers_vat_id_status'] == 1) {
				$iscompany = true;
				$vatid = $row['customers_vat_id'];
			}else {
				$iscompany = false;
				$vatid = '';
			}

			$gesamteContacts[] =    new xmlrpcval(array(
                                                'id' => new xmlrpcval($i, 'int'),
       						'key' => new xmlrpcval('ids', 'string'),
        					'value' => new xmlrpcval($row['customers_id'], 'int')), 'struct');

			$gesamteContacts[] =    new xmlrpcval(array(
					        'id' => new xmlrpcval($i, 'int'),
        					'key' => new xmlrpcval('cname', 'string'),
        					'value' => new xmlrpcval($row['customers_lastname'], 'string')), 'struct');

			$gesamteContacts[] =    new xmlrpcval(array(
						'id' => new xmlrpcval($i, 'int'),
       						'key' => new xmlrpcval('taxnumber', 'string'),
        					'value' => new xmlrpcval($vatid, 'int')), 'struct');
			$gesamteContacts[] =    new xmlrpcval(array(
					        'id' => new xmlrpcval($i, 'int'),
        					'key' => new xmlrpcval('prename', 'string'),
        					'value' => new xmlrpcval($row['customers_firstname'], 'string')), 'struct');
			$gesamteContacts[] =    new xmlrpcval(array(
					        'id' => new xmlrpcval($i, 'int'),
        					'key' => new xmlrpcval('street', 'string'),
        					'value' => new xmlrpcval($row['entry_street_address'], 'string')), 'struct');
			$gesamteContacts[] =    new xmlrpcval(array(
						'id' => new xmlrpcval($i, 'int'),
       						'key' => new xmlrpcval('zip', 'string'),
        					'value' => new xmlrpcval($row['entry_postcode'], 'int')), 'struct');
			$gesamteContacts[] =    new xmlrpcval(array(
						'id' => new xmlrpcval($i, 'int'),
        					'key' => new xmlrpcval('city', 'string'),
        					'value' => new xmlrpcval($row['entry_city'], 'string')), 'struct');
			$gesamteContacts[] =    new xmlrpcval(array(
						'id' => new xmlrpcval($i, 'int'),
       						'key' => new xmlrpcval('mainphone', 'string'),
        					'value' => new xmlrpcval($row['customers_telephone'], 'int')), 'struct');
			$gesamteContacts[] =    new xmlrpcval(array(
						'id' => new xmlrpcval($i, 'int'),
       						'key' => new xmlrpcval('fax', 'string'),
        					'value' => new xmlrpcval($row['customers_fax'], 'int')), 'struct');
			$gesamteContacts[] =    new xmlrpcval(array(
						'id' => new xmlrpcval($i, 'int'),
        					'key' => new xmlrpcval('mailaddress', 'string'),
        					'value' => new xmlrpcval($row['customers_email_address'], 'string')), 'struct');
			$gesamteContacts[] =    new xmlrpcval(array(
						'id' => new xmlrpcval($i, 'int'),
        					'key' => new xmlrpcval('company', 'string'),
        					'value' => new xmlrpcval($row['entry_company'], 'string')), 'struct');
			$gesamteContacts[] =    new xmlrpcval(array(
						'id' => new xmlrpcval($i, 'int'),
        					'key' => new xmlrpcval('country', 'string'),
        					'value' => new xmlrpcval($row['countries_name'], 'string')), 'struct');
			$gesamteContacts[] =    new xmlrpcval(array(
						'id' => new xmlrpcval($i, 'int'),
        					'key' => new xmlrpcval('ismale', 'string'),
        					'value' => new xmlrpcval($gender, 'boolean')), 'struct');
			$gesamteContacts[] =    new xmlrpcval(array(
						'id' => new xmlrpcval($i, 'int'),
        					'key' => new xmlrpcval('iscompany', 'string'),
        					'value' => new xmlrpcval($iscompany, 'boolean')), 'struct');

			$i++;
			}
		return $gesamteContacts;

		} catch (PDOException $e) {
			throw new exception($e->getMessage());
		}

	}

	}
?>