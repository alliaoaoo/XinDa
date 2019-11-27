package com.xinda.Config;

import java.util.Arrays;
import java.util.List;

public class PermissUrlConfig {
    public static final List Provider= Arrays.asList(
            "/product/getSort",
            "/product/add",
            "/product/list",
            "/product/delete",
            "/product/getproduct",
            "/product/update",
            "/provide/upshop",
            "/provide/getshop",
            "/provide/uplogin",
            "/product/online",
            "/product/offline",
            "/provide/delordermessage",
            "/provide/delmessage",
            "/provide/messagelist",
            "/provide/getmessagesum",
            "/provide/ordermessage",
            "/provide/messordersum",
            "/api/orderlist",
            "/api/agree",
            "/api/noagree",
            "/api/memberattribute",
            "/api/getexpenses"
    );

    public static final List Member = Arrays.asList(
            "/member/productlist",
            "/product/getSort",
            "/api/shoplist",
            "/member/addcart",
            "/member/pluscart",
            "/member/lesscart",
            "/member/setbuynum",
            "/member/delcart",
            "/member/cartlist",
            "/member/getshopimg",
            "/member/play",
            "/order/memberlist",
            "/member/getlogin",
            "/member/uplogin",
            "/member/setpassword",
            "/member/judge",
            "/order/commemtnolist",
            "/order/commemtlist",
            "/api/cartsum",
            "/api/orderserviceimg",
            "/api/popular"
    );
    public static final List Admin = Arrays.asList(
            "/admin/productlist",
            "/product/getproduct",
            "/product/agree",
            "/product/noagree",
            "/admin/providerlist",
            "/admin/stop",
            "/admin/through",
            "/admin/memberlist",
            "/admin/orderlist",
            "/api/memberattribute",
            "/api/orderattr",
            "/admin/playlist",
            "/admin/productrecom",
            "/product/getattr",
            "/order/getorderotherattr",
            "/admin/playtotal",
            "/api/getprovider",
            "/admin/setprovider",
            "/admin/getadmin",
            "/admin/upadmin",
            "/product/setrecommemd",
            "/product/setnorecommemd"
    );
}
