/**
 * Created by user on 2015/12/3.
 */
function ccs(obj)
{
    var os = obj.parentNode.parentNode;

    var m = os.cells[2].childNodes[0].value;

    if (m == '')
    {
        m = 0;
    }

    var p = os.cells[3].childNodes[0].value;

    os.cells[4].childNodes[0].value = mul(m, p);

    var tem = os.cells[4].childNodes[0].value;

    if (tem == 'NaN')
    {
        os.cells[4].childNodes[0].value = '';
        return;
    }

    tem = tem + "";

    os.cells[4].childNodes[0].value = formatNum(tem, 2);

    //total();

}

function cc(obj)
{
    ccs(obj);
    //total();
}

function blu(obj)
{
    blus(obj);
    ccs(obj);
}



function blus(obj)
{
    obj.value = trim(obj.value);
    var tem = obj.value;

    if (tem.indexOf('.') == -1)
    {
        if (tem == '')
        {
            obj.value = '0.00';
            return;
        }
        obj.value = tem + '.00';
        return;
    }

    tem += '00';
    obj.value = tem.substring(0, tem.indexOf('.') + 3);
}

function addTr()
{
    var length = document.getElementsByName("productName").length;

    if (length > 15)
    {
        alert('�?多只能有15个产�?');
        return null;
    }

    var table = $O("tables");
    var tr = $O("trCopy");

    trow = 	table.insertRow(-1);

    if (length % 2 == 1)
    {
        trow.className = 'content2';
    }
    else
    {
        trow.className = 'content1';
    }

    for (var i = 0; i < tr.cells.length - 1; i++)
    {
        var tcell = document.createElement("td");

        tcell.innerHTML = tr.cells[i].innerHTML;

        trow.appendChild(tcell);
    }

    tcell = document.createElement("td");

    tcell.innerHTML = '<input type=button value="删除" class=button_class onclick="removeTr(this)">';

    trow.appendChild(tcell);

    //total();

    return trow;
}

function removeTr(obj)
{
    //obj.parentNode.parentNode.removeNode(true);
    //2015/12/4 removeNode() does not work for Chrome
    obj.parentNode.parentNode.parentNode.removeChild(obj.parentNode.parentNode);

    //rows
    var table = $O("tables");

    for (var i = 2; i < table.rows.length; i++)
    {
        if (i % 2 == 1)
        {
            table.rows[i].className = 'content1';
        }
        else
        {
            table.rows[i].className = 'content2';
        }
    }
}

function toUnqueStr1(ele)
{
    return ele.ppid + '-' + ele.pprice + '-' + ele.pstafferid + '-' + ele.pdepotpartid;
}

function toUnqueStr2(ele)
{
    return ele.productid + '-' + ele.price + '-' + ele.stafferid + '-' + ele.depotpartid;
}

function distinc(ox)
{
    //productid
    var plist = document.getElementsByName('productName');

    var arr1 = new Array();

    for(var i = 0; i < plist.length; i++)
    {
        arr1[i] = toUnqueStr2(plist[i]);
    }

    var arr = new Array();
    var n = 0;
    for(var k = 0; k < ox.length; k++)
    {
        var ff = false;
        for(var i = 0; i < arr1.length; i++)
        {
            if (arr1[i] == toUnqueStr1(ox[k]))
            {
                ff = true;
            }
        }

        if (!ff)
        {
            arr1.push(toUnqueStr1(ox[k]));

            arr[n++] = ox[k];
        }
    }

    return arr;
}

function getProductRelation(oxes)
{
	
	var ox = oxes[0];

    var tr = oo.parentNode.parentNode;
    
    getInputInTr(tr, "productName").value = ox.pname;
    getInputInTr(tr, "productId").value = ox.ppid;
    
    
    getInputInTr(tr, "price").value = ox.prealprice;
    getInputInTr(tr, "location").value = ox.plocationid;
    
    getInputInTr(tr, "depotpart").value = ox.pdepotpartname;
    getInputInTr(tr, "depotpartId").value = ox.pdepotpartid;
    //getInputInTr(tr, "storageName").value = ox.pstoragename;    
    getInputInTr(tr, "storageId").value = ox.pstorageid;    
    
    //getInputInTr(tr, "provider").value = ox.pprovidername;
    //getInputInTr(tr, "providerId").value = ox.pproviderid;
    //getInputInTr(tr, "duty").value = ox.pdutyname;
    //etInputInTr(tr, "dutyId").value = ox.pdutyid;
    //getInputInTr(tr, "invoiceType").value = ox.pinvoicetypename;
    //getInputInTr(tr, "invoiceId").value = ox.pinvoicetype;
    
    getInputInTr(tr, "virtualPrice").value = ox.pvirtualprice;
    getInputInTr(tr, "virtualPriceKey").value = ox.pvirtualpricekey;
    
    var backType = getSelectInTr(tr, "backType").value;
    
    var storageAmount = parseInt(ox.pamount);
    var stockAmount = parseInt(ox.pstockammount);
    var totalWarehouseNum = parseInt(ox.ptotalwarehousenum);
    var amount = 0;
    if(backType == '2'){
    	//未入库
    	amount = stockAmount-totalWarehouseNum;
    }else{
    	//已入库
    	amount = storageAmount;
        if(totalWarehouseNum < storageAmount){
        	amount = totalWarehouseNum;
        }    	
    }

    getInputInTr(tr, "amount").value = (0-amount);
    
    //数量上限
    getInputInTr(tr, "amountLimit").value = amount;

    getInputInTr(tr, "amount").title = "库存数量："+storageAmount+",采购数量："+stockAmount+",已入库数量："+totalWarehouseNum;
    
    //initial select
    var providerSelect = getSelectInTr(tr, "providerId");
    providerSelect.options.length = 0;
    
    //console.log(productInfoMap['1']+", productInfoMapSize:"+productInfoMapSize);
    
    for(var k = 1; k <= productInfoMapSize; k++)
    {
    	var item = productInfoMap[k+''];
    	//console.log("k:"+k+", "+item+", "+item['productId']+","+item['productName']+", "+ox.ppid);
    	if(item['productId'] == ox.ppid){
    		setOption(providerSelect, item['providerId'], item['provider']);
    	}
    	
    }
    
    /*
    var productArray = getValuesFromMap(productInfoMap);
    
    setOption(providerSelect, '', '-');
    for(var k = 0; k < productArray.length; k++)
    {
    	if(productArray[k].productId == ox.ppid){
    		setOption(providerSelect, productArray[k].productId, productArray[k].productName);
    	}
    	
    }
    */
    
}

function getValuesFromMap(map){
	  var arr = new Array();
	  alert(map);
	  for (i = 0; i < map.elements.length; i++) {  
	  arr.push(map.elements[i].value);
	  }
	  return arr;
}


function addRow(){
    addTr();
    //showDate();
}

function lverify()
{
    var productIds = document.getElementsByName('productId');
    var amounts = document.getElementsByName('amount');
    var productMap2 = {}
    for (var i=0;i<productIds.length; i++){
        var productIdNode = productIds[i];
        var amountNode = amounts[i];
        var productId = productIdNode.value;
        var amount = amountNode.value;

        if (amount>0){
            if (productId in productMap2){
                productMap2[productId] = parseInt(productMap2[productId])+parseInt(amount)
            } else{
                productMap2[productId] = parseInt(amount)
            }
        }
    }

    //2015/12/20 使用lodash compare object
    if (!_.isEqual(productMap2, productMap)){
            alert("同一商品的到货数量累计必须等于原采购数量");
            return false;
    }
    return true;
}

function addBean(opr)
{
    submit('确定提交采购到货信息?', null, lverify);
}


