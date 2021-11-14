package com.nsss.procurementmanagementsystembackend.constant;

public final class Constants {

    public static final class RequestMapping {
        //Constant for url path
        public static final String REQUEST_MAPPING = "/api/access";

        //Constant for url path of Orders
        public static final String ORDERS = "/orders";

        //Constant for url path of Pending Orders
        public static final String PENDING_ORDERS = "/orders/pending";

        //Constant for url path of Approved Orders
        public static final String APPROVED_ORDERS_ID = "/orders/approved/{id}";

        //Constant for url path of Held Orders
        public static final String HOLD_ORDERS_ID = "/orders/hold/{id}";

        //Constant for url path of Declined Orders
        public static final String DECLINED_ORDERS_ID = "/orders/declined/{id}";

        //Constant for url path of Referred Orders
        public static final String REFERRED_ORDERS_ID = "/orders/referred/{id}";

        //Constant for url path of Delivered Orders
        public static final String DELIVERED_ORDERS_ID = "/orders/delivered/{id}";

        //Constant for url path of Invoices
        public static final String INVOICES = "/invoices";

        //Constant for url path of Materials
        public static final String MATERIALS = "/materials";

        //Constant for url path of Sites
        public static final String SITES = "/sites";

        //Constant for url path of Suppliers
        public static final String SUPPLIERS = "/suppliers";
    }

    public static final class Status {
        //Constant for Pending Status
        public static final String PENDING = "Pending";

        //Constant for Approved Status
        public static final String APPROVED = "Placed";

        //Constant for Hold Status
        public static final String HOLD = "Hold";

        //Constant for Declined Status
        public static final String DECLINED = "Declined";

        //Constant for Referred Status
        public static final String REFERRED = "Referred";

        //Constant for Delivered Status
        public static final String DELIVERED = "Delivered";
    }

    public static final class Common {
        //Common Constant for ID
        public static final String ID = "id";
    }

    public static final class Message{
        //Constant for message when Order is created successfully
        public static final String ORDER_CREATED_SUCCESSFULLY = "Order created successfully";

        //Constant for message when Site is created successfully
        public static final String SITE_CREATED_SUCCESSFULLY = "Site created successfully";

        //Constant for message when Supplier is created successfully
        public static final String SUPPLIER_CREATED_SUCCESSFULLY = "Supplier created successfully";

        //Constant for message when Invoice is created successfully
        public static final String INVOICE_CREATED_SUCCESSFULLY = "Invoice created successfully";

        //Constant for message when Material is created successfully
        public static final String MATERIAL_CREATED_SUCCESSFULLY = "Material created successfully";
    }
}

//package com.nsss.procurementmanagementsystembackend.constant;
//
//
//public final class Constants {
//    public static final class RequestMapping {
//        public static final String REQUEST_MAPPING = "/api/access";
//        public static final String ORDERS = "/orders";
//        public static final String PENDING_ORDERS = "/orders/pending";
//        public static final String APPROVED_ORDERS_ID = "/orders/approved/{id}";
//        public static final String HOLD_ORDERS_ID = "/orders/hold/{id}";
//        public static final String DECLINED_ORDERS_ID = "/orders/declined/{id}";
//        public static final String REFERRED_ORDERS_ID = "/orders/referred/{id}";
//        public static final String DELIVERED_ORDERS_ID = "/orders/delivered/{id}";
//        public static final String SITES = "/sites";
//        public static final String INVOICES = "/invoices";
//        public static final String SUPPLIERS = "/suppliers";
//        public static final String MATERIALS = "/materials";
//    }
//
//    public static final class Status {
//        public static final String PENDING = "Pending";
//        public static final String APPROVED = "Placed";
//        public static final String HOLD = "Hold";
//        public static final String DECLINED = "Declined";
//        public static final String REFERRED = "Referred";
//        public static final String DELIVERED = "Delivered";
//    }
//
//    public static final class Common {
//        public static final String ID = "id";
//    }
//
//    public static final class Message{
//        public static final String ORDER_CREATED_SUCCESSFULLY = "Order created successfully";
//        public static final String SITE_CREATED_SUCCESSFULLY = "Site created successfully";
//        public static final String SUPPLIER_CREATED_SUCCESSFULLY = "Supplier created successfully";
//        public static final String INVOICE_CREATED_SUCCESSFULLY = "Invoice created successfully";
//        public static final String MATERIAL_CREATED_SUCCESSFULLY = "Material created successfully";
//    }
//}