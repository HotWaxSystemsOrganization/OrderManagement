<#-- Find Order Results -->
<#if parameters.orders?has_content>
    <h2>Search Results (${parameters.totalRecords!0} orders found)</h2>
    <table border="1">
        <tr>
            <th>Order ID</th>
            <th>Customer Name</th>
            <th>Order Status</th>
            <th>Payment Status</th>
            <th>address1</th>
            <th>address2</th>
            <th>stateProvinceGeoID</th>
            <th>countryGeoID</th>
            <th>postalCode</th>
            <th>Order Date</th>
        </tr>
        <#list parameters.orders as order>
            <tr>
                <td>${order.orderId!}</td>
                <td>${order.firstName!} ${order.lastName!}</td>
                <td>${order.statusId!}</td>
                <td>${order.paymentStatusId!}</td>
                <td>${order.address1!}</td>
                <td>${order.address2!}</td>
                <td>${order.city!}</td>
                <td>${order.stateProvinceGeoID!}</td>
                <td>${order.countryGeoID!}</td>
                <td>${order.postalCode!}</td>
                <td>${order.orderDate?string("yyyy-MM-dd HH:mm:ss")!}</td>
            </tr>
        </#list>
    </table>
<#else>
    <p>No orders found matching the criteria.</p>
</#if>
