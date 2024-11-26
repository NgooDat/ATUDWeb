<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html style="font-size: 16px;" lang="en">

<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta charset="utf-8">
  <meta name="keywords" content="subscribe">
  <meta name="description" content="">
  <title>Thông tin cá nhân</title>
  <link rel="stylesheet" href="css/page.css" media="screen">

  <meta name="generator" content="Nicepage 7.0.3, nicepage.com">
  <meta name="referrer" content="origin">
  <link id="u-theme-google-font" rel="stylesheet"
    href="https://fonts.googleapis.com/css?family=Roboto:100,100i,300,300i,400,400i,500,500i,700,700i,900,900i|Open+Sans:300,300i,400,400i,500,500i,600,600i,700,700i,800,800i">



  <script type="application/ld+json">{
            "@context": "http://schema.org",
            "@type": "Organization",
            "name": "",
            "logo": "images/pngwing.com.png"
            }</script>
  <meta name="theme-color" content="#478ac9">
  <meta property="og:title" content="Page 1">
  <meta property="og:type" content="website">
  <meta data-intl-tel-input-cdn-path="intlTelInput/">
</head>

<body data-path-to-root="./" data-include-products="true" class="u-body u-xl-mode" data-lang="en">

<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

  <section class="u-clearfix u-section-1" id="sec-4712">
    <div class="wrap u-clearfix u-sheet u-sheet-1">
      <div class="wrap2 u-form u-form-1">
        <form action="#"
          class="wrap3 u-clearfix u-form-spacing-10 u-form-vertical u-inner-form" source="email" name="form"
          style="padding: 10px;">
          <div class="u-form-group u-form-name">
            <label for="name-92f3" class="u-label">Họ và tên</label>
            <input type="text" value="${personal.name }" placeholder="Họ và tên" id="name-92f3" name="name" class="input u-input u-input-rectangle"
              required="">
          </div>
          <div class="u-form-email u-form-group">
            <label for="email-92f3" class="u-label">Số điện thoại</label>
            <input type="email" value="${personal.phone }" placeholder="Số điện thoại" id="email-92f3" name="email"
              class="input u-input u-input-rectangle" required="">
          </div>
          <div class="u-form-email u-form-group">
            <label for="email-92f3" class="u-label">Email</label>
            <input type="email" value="${acc.email }" placeholder="Email" id="email-92f3" name="email"
              class="input u-input u-input-rectangle" required="">
          </div>
          
          <div class="u-align-left u-form-group u-form-submit">
            <a href="#" class="input2 u-btn u-btn-submit u-button-style">Cập nhật</a>
            <input type="submit" value="submit" class="u-form-control-hidden">
          </div>
          
          <input type="hidden" value="" name="recaptchaResponse">
          <input type="hidden" name="formServices" value="d4782bfc-b041-1b72-5f81-a5fd7ed9f8a4">
        </form>
      </div>
      <div style="background-image: url('images/avatar/default.png')" class="u-image u-image-circle u-image-1" alt="" data-image-width="1280" data-image-height="1280">
		<img src="images/avatar/${personal.image}" class="avatar">
		<form class="form-image"  action="#" method="post" enctype="multipart/form-data">
          <input  type="file" id="file" name="file" accept="image/*">
      </form>
		</div>

        

    </div>
  </section>
  
   <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>





  <style>
  
  .wrap{
  	min-height: 80vh!important;
  	position: relative;
  }
  
  .wrap2{
  	position: absolute;
  	padding: 40px 40px 30px 40px;
  	top: 10%;
  	background: #fff;
  	border-radius: 10px;
  	-webkit-box-shadow: 10px 10px 5px 0px #cccccc;
  -moz-box-shadow: 10px 10px 5px 0px #cccccc;
  box-shadow: 10px 10px 5px 0px #cccccc;
  	  }
  
  section{
  	background: #eee;
  }
  
  .avatar{
  width: 200px;
  height: 200px;
  border-radius: 50%;
  object-fit: cover;    /* Đảm bảo ảnh phủ toàn bộ thẻ mà không bị méo */
  display: block;       /* Xóa bỏ khoảng cách dưới ảnh (nếu có) */
  margin: 0 auto;
  }
  
  wrap3{
  	top: 10%;
  }
  
  .form-image{
  	position: absolute; top: 100%;
  	transform: translateY(12px);
  }
  
  .u-form.u-form-2{
  	position: absolute;
  }
  
  .u-section-1 .u-sheet-1 {
  min-height: 498px;
}

.u-section-1 .u-form-1 {
  height: 419px;
  margin: 29px 68px 0 413px;
}

.u-section-1 .u-image-1 {
  width: 200px;
  height: 200px;
     position: absolute;
   top: 20%;
   left: 8%;
  background-position: 50% 50%;
  background-size: cover;
  -webkit-box-shadow: 10px 10px 5px 0px #cccccc;
  -moz-box-shadow: 10px 10px 5px 0px #cccccc;
  box-shadow: 10px 10px 5px 0px #cccccc;
}

.input{
	border-radius: 10px!important;
}

.input2{
	border-radius: 20px!important;
	left: 42%;
}

.u-section-1 .u-form-2 {
  height: 68px;
  margin: 20px 799px 60px 48px;
}

@media (max-width: 1199px) {
  .u-section-1 .u-form-1 {
    margin-right: 0;
    margin-left: 281px;
  }

  .u-section-1 .u-form-2 {
    margin-right: 647px;
    margin-left: 0;
  }
}

@media (max-width: 991px) {
  .u-section-1 .u-form-1 {
    margin-left: 61px;
  }

  .u-section-1 .u-form-2 {
    margin-right: 427px;
  }
}

@media (max-width: 767px) {
  .u-section-1 .u-form-1 {
    margin-left: 0;
  }

  .u-section-1 .u-form-2 {
    margin-right: 247px;
  }
}

@media (max-width: 575px) {
  .u-section-1 .u-image-1 {
    margin-left: 40px;
  }

  .u-section-1 .u-form-2 {
    margin-right: 47px;
  }
}
    .u-dialog-section-4 .u-dialog-1 {
      width: 570px;
      min-height: 578px;
      height: auto;
      box-shadow: 0px 0px 8px 0px rgba(0, 0, 0, 0.2);
      margin: 60px auto;
    }

    .u-dialog-section-4 .u-container-layout-1 {
      padding: 34px 35px;
    }

    .u-dialog-section-4 .u-text-1 {
      font-weight: 700;
      margin: 0 165px 0 0;
    }

    .u-dialog-section-4 .u-products-1 {
      margin-top: 30px;
      margin-bottom: 0;
    }

    .u-dialog-section-4 .u-repeater-1 {
      grid-template-columns: 100%;
      min-height: 206px;
      grid-gap: 10px;
    }

    .u-dialog-section-4 .u-container-layout-2 {
      padding: 0 0 30px;
    }

    .u-dialog-section-4 .u-text-2 {
      background-image: none;
      margin: 0;
    }

    .u-dialog-section-4 .u-text-3 {
      font-size: 0.875rem;
      margin: 20px 0 0;
    }

    .u-dialog-section-4 .u-product-quantity-1 {
      width: 125px;
      margin: 30px auto 0 0;
    }

    .u-dialog-section-4 .u-product-price-1 {
      margin: -34px 0 0 auto;
    }

    .u-dialog-section-4 .u-payment-services-1 {
      min-height: 250px;
      min-width: 50px;
      margin: 0;
    }

    .u-dialog-section-4 .u-container-layout-3 {
      padding: 20px 0 0;
    }

    .u-dialog-section-4 .u-container-layout-4 {
      padding: 20px 0 0;
    }

    .u-dialog-section-4 .u-btn-1 {
      width: 100%;
      margin: 0 auto;
    }

    .u-dialog-section-4 .u-icon-1 {
      width: 20px;
      height: 20px;
      left: auto;
      top: 36px;
      position: absolute;
      right: 35px;
      padding: 0;
    }

    @media (max-width: 1199px) {
      .u-dialog-section-4 .u-text-1 {
        margin-right: 165px;
      }

      .u-dialog-section-4 .u-payment-services-1 {
        margin-right: initial;
        margin-left: initial;
      }
    }

    @media (max-width: 991px) {
      .u-dialog-section-4 .u-container-layout-1 {
        padding: 30px;
      }

      .u-dialog-section-4 .u-container-layout-3 {
        padding-bottom: 0;
        padding-left: 0;
        padding-right: 0;
      }

      .u-dialog-section-4 .u-container-layout-4 {
        padding-bottom: 0;
        padding-left: 0;
        padding-right: 0;
      }

      .u-dialog-section-4 .u-icon-1 {
        top: 32px;
        right: 30px;
      }
    }

    @media (max-width: 767px) {
      .u-dialog-section-4 .u-dialog-1 {
        width: 540px;
      }
    }

    @media (max-width: 575px) {
      .u-dialog-section-4 .u-dialog-1 {
        width: 340px;
      }

      .u-dialog-section-4 .u-container-layout-1 {
        padding-left: 20px;
        padding-right: 20px;
      }

      .u-dialog-section-4 .u-text-1 {
        margin-right: 5px;
      }

      .u-dialog-section-4 .u-icon-1 {
        right: 20px;
      }
    }
  </style>
</body>

</html>