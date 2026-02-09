/* Application Name = Hire A Bus
 Version = 1.0
 Release Date = March 01, 2015
 Copyright Owner =  ©2014 SRISYS Inc
 Developed by = Srisys Inc, 7908 Cincinnati Dayton Rd, Suite C, West Chester, OH 45069 USA
 Web: www.srisys.com Email: info@srisys.com */

 function clearmessage() {
     document.getElementById("errormsg").innerHTML = "";
  }
 function isNumberKey(evt) {
     var charCode = (evt.which) ? evt.which : event.keyCode;
     if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
     else
        return true;
  }
 function getcash(cash_div_id, cheque_div_id, transfer_div_id, card_div_id, paypal_div_id) {
     var cash = document.getElementById(cash_div_id);
     var cheque = document.getElementById(cheque_div_id);
     var transfer = document.getElementById(transfer_div_id);
     var card = document.getElementById(card_div_id);
     var paypal = document.getElementById(paypal_div_id);
     if (cash.style.display === 'none') {
        cash.style.display = 'block';
        cheque.style.display = 'none';
        transfer.style.display = 'none';
        card.style.display = 'none';
        paypal.style.display = 'none';
        document.bushirepayform.action = "updateCashPayment";
       // document.bushirepayform.action = "<?= $this->config->base_url(); ?>ctrl_bushires/updatePayment";
     }
  }
  function getcheque(cash_div_id, cheque_div_id, transfer_div_id, card_div_id, paypal_div_id) {
     var cash = document.getElementById(cash_div_id);
     var cheque = document.getElementById(cheque_div_id);
     var transfer = document.getElementById(transfer_div_id);
     var card = document.getElementById(card_div_id);
     var paypal = document.getElementById(paypal_div_id);
     if (cheque.style.display === 'none') {
        cash.style.display = 'none';
        cheque.style.display = 'block';
        transfer.style.display = 'none';
        card.style.display = 'none';
        paypal.style.display = 'none';
        document.bushirepayform.action = "updateChequePayment";
        //document.bushirepayform.action = "<?= $this->config->base_url(); ?>ctrl_bushires/updatePayment";
     }
  }
  function gettransfer(cash_div_id, cheque_div_id, transfer_div_id, card_div_id, paypal_div_id) {
     var cash = document.getElementById(cash_div_id);
     var cheque = document.getElementById(cheque_div_id);
     var transfer = document.getElementById(transfer_div_id);
     var card = document.getElementById(card_div_id);
     var paypal = document.getElementById(paypal_div_id);
     if (transfer.style.display === 'none') {
        cash.style.display = 'none';
        cheque.style.display = 'none';
        transfer.style.display = 'block';
        card.style.display = 'none';
        paypal.style.display = 'none';
        document.bushirepayform.action = "updateTransferPayment";
        //document.bushirepayform.action = "<?= $this->config->base_url(); ?>ctrl_bushires/updatePayment";
     }
  }
  function getcard(cash_div_id, cheque_div_id, transfer_div_id, card_div_id, paypal_div_id) {
     var cash = document.getElementById(cash_div_id);
     var cheque = document.getElementById(cheque_div_id);
     var transfer = document.getElementById(transfer_div_id);
     var card = document.getElementById(card_div_id);
     var paypal = document.getElementById(paypal_div_id);
     if (card.style.display === 'none') {
        cash.style.display = 'none';
        cheque.style.display = 'none';
        transfer.style.display = 'none';
        card.style.display = 'block';
        paypal.style.display = 'none';
        document.bushirepayform.action = "updateCardPayment";
        //document.bushirepayform.action = "<?= $this->config->base_url(); ?>ctrl_bushires/updatePayment";
     }
  }
     function getpaypal(cash_div_id, cheque_div_id, transfer_div_id, card_div_id, paypal_div_id) {
     var cash = document.getElementById(cash_div_id);
     var cheque = document.getElementById(cheque_div_id);
     var transfer = document.getElementById(transfer_div_id);
     var card = document.getElementById(card_div_id);
     var paypal = document.getElementById(paypal_div_id);
     if (paypal.style.display === 'none') {
        cash.style.display = 'none';
        cheque.style.display = 'none';
        transfer.style.display = 'none';
        card.style.display = 'none';
        paypal.style.display = 'block';
        document.bushirepayform.action = "updatePaypalPayment";
        //document.bushirepayform.action = "<?= $this->config->base_url(); ?>ctrl_bushires/paypal";
     }
  }
     function payment() {
         var pays1 = document.getElementById('numberOfRows').value;
         var sta = document.getElementById('status').value;
         var amt_price = document.getElementById('amt_price_id').value;
         pays1 = parseInt(pays1);
         if ((document.getElementById("add_cash_validate_id" + pays1).checked !== true && document.getElementById("add_cheque_validate_id" + pays1).checked !== true && document.getElementById("add_transfer_validate_id" + pays1).checked !== true && document.getElementById("add_card_validate_id" + pays1).checked !== true && document.getElementById("add_paypal_validate_id" + pays1).checked !== true)) {
            document.getElementById("errormsg").innerHTML = "Payment can not be blank";
            return false;
         }
         for (var pays = 1; pays <= pays1; pays++) {
           if (document.getElementById("add_cash_validate_id" + pays).checked === true) {
               var cashamount = document.getElementById('cash_amount_id' + pays).value;
               cashamount = $.trim(cashamount);
               if (cashamount === '' || cashamount === null) {
                  document.getElementById('errormsg').innerHTML = "Amount can not be blank";
                  return false;
               }else  if (parseFloat(cashamount)=='0') {
                  document.getElementById('errormsg').innerHTML = "Amount can not be zero";
                  return false;
               }else  if (sta != 'Bus Hire Ended' && parseFloat(cashamount) >= parseFloat(amt_price)) {
                  document.getElementById('errormsg').innerHTML = "Amount  must be lessthan AMT Estim Price";
                  return false;
               }
            }
           if (document.getElementById("add_paypal_validate_id" + pays).checked === true) {
               var paypalamount = document.getElementById('paypal_amount_id' + pays).value;
               paypalamount = $.trim(paypalamount);
               if (paypalamount === '' || paypalamount === null) {
                  document.getElementById('errormsg').innerHTML = "Amount can not be blank";
                  return false;
               }else  if (parseFloat(paypalamount)=='0') {
                  document.getElementById('errormsg').innerHTML = "Amount can not be zero";
                  return false;
               }else  if (sta != 'Bus Hire Ended' && parseFloat(paypalamount) >= parseFloat(amt_price)) {
                  document.getElementById('errormsg').innerHTML = "Amount  must be lessthan AMT Estim Price";
                  return false;
               }
            }         
            if (document.getElementById("add_cheque_validate_id" + pays).checked === true) {
               var cheque_num = document.getElementById('cheque_num_id' + pays).value.trim();
               var cheque_bank = document.getElementById('cheque_bank_id' + pays).value.trim();
               var cheque_amount = document.getElementById('cheque_amount_id' + pays).value.trim();
               if (cheque_num === '' || cheque_num === null) {
                  document.getElementById('errormsg').innerHTML = "Cheque Number can not be blank";
                  return false;
               } else if (cheque_bank === '' || cheque_bank === null) {
                  document.getElementById('errormsg').innerHTML = "Bank Name can not be blank";
                  return false;
               } else if (cheque_amount === '' || cheque_amount === null) {
                  document.getElementById('errormsg').innerHTML = "Amount can not be blank";
                  return false;
               }else  if (parseFloat(cheque_amount)=='0') {
                  document.getElementById('errormsg').innerHTML = "Amount can not be zero";
                  return false;
               }else  if (parseFloat(sta != 'Bus Hire Ended' && cheque_amount) >= parseFloat(amt_price)) {
                  document.getElementById('errormsg').innerHTML = "Amount  must be lessthan AMT Estim Price";
                  return false;
               }
            }
            if (document.getElementById("add_transfer_validate_id" + pays).checked === true) {
               var transfer_cnum = document.getElementById('transfer_cnum_id' + pays).value.trim();
               var transfer_bank = document.getElementById('transfer_bank_id' + pays).value.trim();
               var transfer_amount = document.getElementById('transfer_amount_id' + pays).value.trim();
               if (transfer_cnum === '' || transfer_cnum === null) {
                  document.getElementById('errormsg').innerHTML = "Account Number can not be blank";
                  return false;
               } else if (transfer_bank === '' || transfer_bank === null) {
                  document.getElementById('errormsg').innerHTML = "Bank can not be blank";
                  return false;
               } else if (transfer_amount === '' || transfer_amount === null) {
                  document.getElementById('errormsg').innerHTML = "Amount can not be blank";
                  return false;
               }else  if (parseFloat(transfer_amount)=='0') {
                  document.getElementById('errormsg').innerHTML = "Amount can not be zero";
                  return false;
               }else  if (sta != 'Bus Hire Ended' && parseFloat(transfer_amount) >= parseFloat(amt_price)) {
                  document.getElementById('errormsg').innerHTML = "Amount  must be lessthan AMT Estim Price";
                  return false;
               }
            }
            if (document.getElementById("add_card_validate_id" + pays).checked === true) {
               var card_cardtype = document.getElementById('card_cardtype_id' + pays).value.trim();
               var card_cardnum = document.getElementById('card_cardnum_id' + pays).value.trim();
               var card_cvv = document.getElementById('card_cvv_id' + pays).value.trim();
                var card_month_date = document.getElementById('card_month_id'+pays).value.trim();
                var card_year_date = document.getElementById('card_year_id'+pays).value.trim();
               var card_amount = document.getElementById('card_amount_id' + pays).value.trim();
               if (card_cardtype === '' ) {
                  document.getElementById('errormsg').innerHTML = "Card Type can not be blank";
                  return false;
               } else if (card_cardnum === '' || card_cardnum === null) {
                  document.getElementById('errormsg').innerHTML = "Card Number can not be blank";
                  return false;
               } else if (card_cvv === '' || card_cvv === null) {
                  document.getElementById('errormsg').innerHTML = "CVV Number can not be blank";
                  return false;
               } else if (card_month_date === '' ) {
                document.getElementById('errormsg').innerHTML = "Expire Month can not be blank";
                return false;
                }else if (card_year_date === '' ) {
                document.getElementById('errormsg').innerHTML = "Expire Year can not be blank";
                return false;
                } else if (card_amount === '' || card_amount === null) {
                  document.getElementById('errormsg').innerHTML = "Amount can not be blank";
                  return false;
               }else  if (parseFloat(card_amount)=='0') {
                  document.getElementById('errormsg').innerHTML = "Amount can not be zero";
                  return false;
               }else  if (sta != 'Bus Hire Ended' && parseFloat(card_amount) >= parseFloat(amt_price)) {
                  document.getElementById('errormsg').innerHTML = "Amount  must be lessthan AMT Estim Price";
                  return false;
               }
            }
          if(sta == 'Bus Hire Ended') {
                  var final=document.getElementById('final_id').value; 
                  var cmp1=document.getElementById('cash_amount_id1').value; 
                  var cmp2=document.getElementById('cheque_amount_id1').value; 
                  var cmp3=document.getElementById('transfer_amount_id1').value; 
                  var cmp4=document.getElementById('card_amount_id1').value; 
                  if(final > cmp1 || final > cmp2 || final > cmp3 || final > cmp4){
                      document.getElementById('errormsg').innerHTML = "Amount can not be less than actual price";
                      return false;
                   }else if(final < cmp1 || final < cmp2 || final < cmp3 || final < cmp4){
                      document.getElementById('errormsg').innerHTML = "Amount can not be more than actual price";
                      return false;
                   }
             }
         }
         return true;
      }
     function addPayment() {
         if (payment()) {
            document.getElementById('bushirepayform').submit();
            return true;
         } else
            return false;
      }
     function hyphen_generate(evt, value, id) {
         if (value.length === 2)
            if (document.getElementById(id).value > 12) {
               document.getElementById(id).value = null;
               document.getElementById("errormsg").innerHTML = "Month must be less than or equal to 12";
               return false;
            } else
               document.getElementById(id).value = value.concat("/");
      }
       function decimal_validation(value, evt) {
         var charCode = (evt.which) ? evt.which : event.keyCode;
         if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
            return false;
         }
         if (charCode == 46 && value.indexOf('.') !== -1) {
            return false;
         }
         return true;
      }
