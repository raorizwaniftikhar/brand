#Enter in mongoDb pod

kubectl exec -it brand-mongodb-0 sh

#Get mongo client pod shell
 mongo
 
#Select Database
use Brand;

#insert Data into collection/table

	db.brand.insert({"type": "brands",
		  "name": "Clothing",
		  "slug": "cool-clothing",
		  "description": "Cool clothing make cool clothes!",
		  "status": "live"
		  });
          
#Update Single Record
db.brand.update({'name':'Clothing'},{$set:{'name':'New Clothing'}})

#Get single Record
db.brand.findOne({'name': 'New Clothing'});

Change the field name for Diffrent type Search.like a By slug,status or _id
#Delete Single data
db.brand.remove({'name': 'New Clothing'});

#Delete All Record

db.brand.remove({});

#Get All Brand
db.brand.find().pretty();
