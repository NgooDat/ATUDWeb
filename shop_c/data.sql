USE [shop_c]
GO
SET IDENTITY_INSERT [dbo].[Rules] ON 

INSERT [dbo].[Rules] ([ID], [name]) VALUES (1, N'Admin')
INSERT [dbo].[Rules] ([ID], [name]) VALUES (2, N'User')
SET IDENTITY_INSERT [dbo].[Rules] OFF
GO
SET IDENTITY_INSERT [dbo].[accounts] ON 

INSERT [dbo].[accounts] ([ID], [email], [password], [status], [rulesID]) VALUES (1, N'user1@example.com', N'dat123', 1, 1)
INSERT [dbo].[accounts] ([ID], [email], [password], [status], [rulesID]) VALUES (2, N'user2@example.com', N'password456', 1, 2)
INSERT [dbo].[accounts] ([ID], [email], [password], [status], [rulesID]) VALUES (4, N'test@gmail.te', N'test', 1, 2)
INSERT [dbo].[accounts] ([ID], [email], [password], [status], [rulesID]) VALUES (5, N'dd', N'dd', 1, 2)
SET IDENTITY_INSERT [dbo].[accounts] OFF
GO
SET IDENTITY_INSERT [dbo].[customers] ON 

INSERT [dbo].[customers] ([ID], [name], [phone], [image], [accountsID]) VALUES (1, N'Alice Johnson', N'555-123-4567', N'anh2.jpg', 1)
INSERT [dbo].[customers] ([ID], [name], [phone], [image], [accountsID]) VALUES (2, N'Bob Brown', N'555-987-6543', N'images/bob.jpg', 2)
INSERT [dbo].[customers] ([ID], [name], [phone], [image], [accountsID]) VALUES (3, N'hioio', N'0001122', N'duong 23', 4)
SET IDENTITY_INSERT [dbo].[customers] OFF
GO
SET IDENTITY_INSERT [dbo].[staffs] ON 

INSERT [dbo].[staffs] ([ID], [name], [phone], [address], [status], [accountsID]) VALUES (1, N'John Doe', N'123-456-7890', N'123 Main St', 1, 1)
INSERT [dbo].[staffs] ([ID], [name], [phone], [address], [status], [accountsID]) VALUES (2, N'Jane Smith', N'987-654-3210', N'456 Elm St', 1, 2)
SET IDENTITY_INSERT [dbo].[staffs] OFF
GO
SET IDENTITY_INSERT [dbo].[brands] ON 

INSERT [dbo].[brands] ([ID], [name]) VALUES (1, N'Nike')
INSERT [dbo].[brands] ([ID], [name]) VALUES (2, N'Adidas')
SET IDENTITY_INSERT [dbo].[brands] OFF
GO
SET IDENTITY_INSERT [dbo].[materials] ON 

INSERT [dbo].[materials] ([ID], [name]) VALUES (1, N'da')
INSERT [dbo].[materials] ([ID], [name]) VALUES (2, N'vai')
SET IDENTITY_INSERT [dbo].[materials] OFF
GO
SET IDENTITY_INSERT [dbo].[origins] ON 

INSERT [dbo].[origins] ([ID], [name]) VALUES (1, N'China')
INSERT [dbo].[origins] ([ID], [name]) VALUES (2, N'USA')
SET IDENTITY_INSERT [dbo].[origins] OFF
GO
SET IDENTITY_INSERT [dbo].[types] ON 

INSERT [dbo].[types] ([ID], [name]) VALUES (1, N'Electronics')
INSERT [dbo].[types] ([ID], [name]) VALUES (2, N'Clothing')
SET IDENTITY_INSERT [dbo].[types] OFF
GO
SET IDENTITY_INSERT [dbo].[products] ON 

INSERT [dbo].[products] ([ID], [name], [image], [description], [typesID], [originsID], [brandsID], [materialsID]) VALUES (1, N'Product A', N'anh1.jpg', N'Description of Product A', 1, 1, 1, 1)
INSERT [dbo].[products] ([ID], [name], [image], [description], [typesID], [originsID], [brandsID], [materialsID]) VALUES (2, N'Product B', N'anh2.jpg', N'Description of Product B', 2, 2, 2, 2)
SET IDENTITY_INSERT [dbo].[products] OFF
GO
SET IDENTITY_INSERT [dbo].[feedbacks] ON 

INSERT [dbo].[feedbacks] ([ID], [description], [customersID], [productsID], [create_time]) VALUES (1, N'S?n ph?m r?t t?t, tôi r?t hài l?ng!', 1, 1, CAST(N'2024-11-22T12:50:59.933' AS DateTime))
INSERT [dbo].[feedbacks] ([ID], [description], [customersID], [productsID], [create_time]) VALUES (2, N'Ch?t lư?ng s?n ph?m t?t, nhưng giao hàng ch?m.', 2, 2, CAST(N'2024-11-22T12:50:59.933' AS DateTime))
INSERT [dbo].[feedbacks] ([ID], [description], [customersID], [productsID], [create_time]) VALUES (7, N'hahah99999', 1, 2, CAST(N'2024-11-25T16:15:07.070' AS DateTime))
SET IDENTITY_INSERT [dbo].[feedbacks] OFF
GO
SET IDENTITY_INSERT [dbo].[order_status] ON 

INSERT [dbo].[order_status] ([id], [statusName]) VALUES (1, N'Pending')
INSERT [dbo].[order_status] ([id], [statusName]) VALUES (2, N'Processing')
INSERT [dbo].[order_status] ([id], [statusName]) VALUES (3, N'Shipped')
INSERT [dbo].[order_status] ([id], [statusName]) VALUES (4, N'Delivered')
INSERT [dbo].[order_status] ([id], [statusName]) VALUES (5, N'Canceled')
SET IDENTITY_INSERT [dbo].[order_status] OFF
GO
SET IDENTITY_INSERT [dbo].[payment_methods] ON 

INSERT [dbo].[payment_methods] ([ID], [name]) VALUES (1, N'cash')
INSERT [dbo].[payment_methods] ([ID], [name]) VALUES (2, N'atm')
INSERT [dbo].[payment_methods] ([ID], [name]) VALUES (3, N'credit')
INSERT [dbo].[payment_methods] ([ID], [name]) VALUES (4, N'Credit Card')
INSERT [dbo].[payment_methods] ([ID], [name]) VALUES (5, N'PayPal')
INSERT [dbo].[payment_methods] ([ID], [name]) VALUES (6, N'Bank Transfer')
INSERT [dbo].[payment_methods] ([ID], [name]) VALUES (7, N'Cash on Delivery')
SET IDENTITY_INSERT [dbo].[payment_methods] OFF
GO
SET IDENTITY_INSERT [dbo].[orders] ON 

INSERT [dbo].[orders] ([id], [create_time], [update_time], [ship_fee], [product_fee], [total], [payment_status], [address], [description], [payment_methodsID], [order_statusID], [customersID]) VALUES (1, CAST(N'2024-11-22T12:49:32.547' AS DateTime), CAST(N'2024-11-25T16:15:07.093' AS DateTime), CAST(50.00 AS Decimal(10, 2)), CAST(200.00 AS Decimal(10, 2)), CAST(250.00 AS Decimal(10, 2)), 1, N'123 Main Street, City', N'This is a test order for sample data', 1, 1, 1)
INSERT [dbo].[orders] ([id], [create_time], [update_time], [ship_fee], [product_fee], [total], [payment_status], [address], [description], [payment_methodsID], [order_statusID], [customersID]) VALUES (2, CAST(N'2024-11-22T12:49:32.547' AS DateTime), CAST(N'2024-11-25T16:15:07.093' AS DateTime), CAST(30.00 AS Decimal(10, 2)), CAST(150.00 AS Decimal(10, 2)), CAST(180.00 AS Decimal(10, 2)), 0, N'456 Secondary Road, Town', N'Sample order for another customer', 2, 2, 2)
INSERT [dbo].[orders] ([id], [create_time], [update_time], [ship_fee], [product_fee], [total], [payment_status], [address], [description], [payment_methodsID], [order_statusID], [customersID]) VALUES (5, CAST(N'2024-11-23T12:18:57.473' AS DateTime), CAST(N'2024-11-25T16:15:07.093' AS DateTime), CAST(2000.00 AS Decimal(10, 2)), CAST(23000.00 AS Decimal(10, 2)), CAST(25000.00 AS Decimal(10, 2)), 1, N'123hht', N'hhwwwwtt', 1, 1, 1)
SET IDENTITY_INSERT [dbo].[orders] OFF
GO
INSERT [dbo].[sizes] ([ID], [name]) VALUES (N'L', N'Large')
INSERT [dbo].[sizes] ([ID], [name]) VALUES (N'M', N'Medium')
INSERT [dbo].[sizes] ([ID], [name]) VALUES (N'S', N'Small')
INSERT [dbo].[sizes] ([ID], [name]) VALUES (N'XL', N'X-Large')
GO
SET IDENTITY_INSERT [dbo].[product_details] ON 

INSERT [dbo].[product_details] ([ID], [quantity], [price], [productsID], [sizesID]) VALUES (1, 100, 15000, 1, N'S')
INSERT [dbo].[product_details] ([ID], [quantity], [price], [productsID], [sizesID]) VALUES (2, 150, 18000, 1, N'M')
INSERT [dbo].[product_details] ([ID], [quantity], [price], [productsID], [sizesID]) VALUES (3, 200, 14000, 2, N'L')
INSERT [dbo].[product_details] ([ID], [quantity], [price], [productsID], [sizesID]) VALUES (4, 50, 17000, 2, N'XL')
SET IDENTITY_INSERT [dbo].[product_details] OFF
GO
SET IDENTITY_INSERT [dbo].[carts] ON 

INSERT [dbo].[carts] ([ID], [quantity], [total_price], [status], [customersID], [product_detailsID]) VALUES (1, 15, CAST(123400.00 AS Decimal(10, 2)), N'1', 1, 1)
INSERT [dbo].[carts] ([ID], [quantity], [total_price], [status], [customersID], [product_detailsID]) VALUES (2, 3, CAST(22300.00 AS Decimal(10, 2)), N'1', 2, 2)
SET IDENTITY_INSERT [dbo].[carts] OFF
GO
INSERT [dbo].[order_details] ([ordersID], [product_detailsID], [quantity], [unit_price]) VALUES (1, 1, 2, CAST(100.00 AS Decimal(10, 2)))
INSERT [dbo].[order_details] ([ordersID], [product_detailsID], [quantity], [unit_price]) VALUES (1, 2, 1, CAST(50.00 AS Decimal(10, 2)))
INSERT [dbo].[order_details] ([ordersID], [product_detailsID], [quantity], [unit_price]) VALUES (2, 1, 3, CAST(100.00 AS Decimal(10, 2)))
GO
