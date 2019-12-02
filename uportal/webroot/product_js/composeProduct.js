function addTr()
{
    for (var i = 0; i < 1; i++)
    {
        addTrInner();
    }
}

function addTrInner()
{
    var table = $O("tables");
    
    var tr = $O("trCopy");
    
    trow =  table.insertRow(-1);
    
    if (length % 2 == 1)
    {
        trow.className = 'content2';
    }
    else
    {
        trow.className = 'content1';
    }
    
    for (var i = 0; i < tr.cells.length; i++)
    {
        var tcell = document.createElement("td");
        
        tcell.innerHTML = tr.cells[i].innerHTML;
        
        trow.appendChild(tcell);
    }
    
    trow.appendChild(tcell);
    
    return trow;
}

function removeTr(obj)
{
    //rows
    var table = $O("tables");
    
    if (table.rows.length <= 2)
    {
    	alert('不能全部删除');
    	
    	return false;
    }
    
    // obj.parentNode.parentNode.removeNode(true);
    //#431 2017/3/11 removeNode() does not work for Chrome
    obj.parentNode.parentNode.parentNode.removeChild(obj.parentNode.parentNode);
    
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

function amountChange(){
    var bomAmount = document.querySelectorAll('input[name="bomAmount"]');
    var useAmount = document.querySelectorAll('input[name="useAmount"]');
//    console.log(srcAmount);
    var amount = document.querySelector('input[name="dirAmount"]');
//    console.log(amount.value);
    // console.log(rateList);
    // var total = 0;
    for (var i = 0 ; i < bomAmount.length; i++)
    {
        var oo = useAmount[i];
        //合成数量*组成用量
        oo.value = parseInt(amount.value)*parseInt(bomAmount[i].value);
    }
}

function computePrice()
{
    var formData = $("#formEntry").serialize();
   console.log("formData***"+formData);
    $.ajax({
        type: "POST",
        url: '../product/product.do?method=computePrice',
        data: formData, // serializes the form's elements.
        success: function(data)
        {
//            alert(data);
           console.log(data);
            var jsonData = JSON.parse(data);
            $O('price').innerHTML = "合成价格:"+jsonData.msg.price;
        }
    });
}