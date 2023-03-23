// 0A2CAF8E40CB8D3B41867F4BBCC9F8163D68



function sendMail() {

  Email.send({
      Host : "smtp.elasticemail.com",
      Username : "pizzarecruitment1234@gmail.com",
      Password : "0A2CAF8E40CB8D3B41867F4BBCC9F8163D68",
      To : 'thzarts@gmail.com',
      From : 'pizzarecruitment1234@gmail.com',
      Subject : "Testing",
      Body : "this is body"
  }).then(
    message => alert(message)
  )

  return true;
}