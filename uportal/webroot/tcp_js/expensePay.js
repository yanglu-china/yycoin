function checkMoney1(){
    var payTypeElement = document.getElementById("payType");
    if(!payTypeElement){
    	payTypeElement = document.getElementById("payType1");
    }
    var payType = payTypeElement.value;
    // console.log(payType);
    //公司付款给员工
    if (parseInt(payType) == 1){
        //原申请借款金额
        var refIdElement = document.getElementById("refId");
        var refId = refIdElement.value;
        var refMoney = parseFloat(document.getElementById("refMoney").value)/100;

        //#570 收款稽核金额
        var pElements = document.getElementsByName('p_cmoneys');

        var pTotal = 0.0;

        for (var i = 0; i<pElements.length; i++)
        {
            if (pElements[i].value != '')
            {
                pTotal += parseFloat(pElements[i].value);
            }
        }

        //费用明细稽核金额
        var fElements = document.getElementsByName('f_cmoneys');

        var fTotal = 0.0;

        for (var i = 0; i<fElements.length; i++)
        {
            if (fElements[i].value != '')
            {
                fTotal += parseFloat(fElements[i].value);
            }
        }
        // console.log(refMoney);
        // console.log(pTotal);
        // console.log(fTotal);
        if (refId && compareDouble(refMoney+pTotal, fTotal)!=0){
            alert('原申请借款金额与收款明细稽核金额之和不等于费用明细稽核金额!');
            return false;
        }
    }

    // else if (isNull(refId) && pTotal>0 && compareDouble(pTotal, fTotal) != 0){
    //     alert('费用明细和收款明细稽核金额不一致!');
    //     return false;
    // }
    return true;
}