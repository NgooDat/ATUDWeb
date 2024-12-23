
/****** Object:  Database [shop_c]    Script Date: 11/23/2024 1:17:20 PM ******/
CREATE DATABASE [shop_c]

GO
ALTER DATABASE [shop_c] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [shop_c] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [shop_c] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [shop_c] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [shop_c] SET ARITHABORT OFF 
GO
ALTER DATABASE [shop_c] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [shop_c] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [shop_c] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [shop_c] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [shop_c] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [shop_c] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [shop_c] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [shop_c] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [shop_c] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [shop_c] SET  DISABLE_BROKER 
GO
ALTER DATABASE [shop_c] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [shop_c] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [shop_c] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [shop_c] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [shop_c] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [shop_c] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [shop_c] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [shop_c] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [shop_c] SET  MULTI_USER 
GO
ALTER DATABASE [shop_c] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [shop_c] SET DB_CHAINING OFF 
GO
ALTER DATABASE [shop_c] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [shop_c] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [shop_c] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [shop_c] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [shop_c] SET QUERY_STORE = ON
GO
ALTER DATABASE [shop_c] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [shop_c]
GO
/****** Object:  Table [dbo].[accounts]    Script Date: 11/23/2024 1:17:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[accounts](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[email] [varchar](255) NOT NULL,
	[password] [varchar](255) NOT NULL,
	[status] [int] NULL,
	[rulesID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[brands]    Script Date: 11/23/2024 1:17:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[brands](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[carts]    Script Date: 11/23/2024 1:17:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[carts](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[quantity] [int] NOT NULL,
	[total_price] [decimal](10, 2) NOT NULL,
	[status] [varchar](50) NOT NULL,
	[customersID] [int] NOT NULL,
	[product_detailsID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[customers]    Script Date: 11/23/2024 1:17:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[customers](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](255) NOT NULL,
	[phone] [varchar](255) NOT NULL,
	[image] [varchar](255) NULL,
	[accountsID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[feedbacks]    Script Date: 11/23/2024 1:17:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[feedbacks](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[description] [nvarchar](255) NULL,
	[customersID] [int] NOT NULL,
	[productsID] [int] NOT NULL,
	[create_time] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[materials]    Script Date: 11/23/2024 1:17:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[materials](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[order_details]    Script Date: 11/23/2024 1:17:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order_details](
	[ordersID] [int] NOT NULL,
	[product_detailsID] [int] NOT NULL,
	[quantity] [int] NOT NULL,
	[unit_price] [decimal](10, 2) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ordersID] ASC,
	[product_detailsID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[order_status]    Script Date: 11/23/2024 1:17:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order_status](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[statusName] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[orders]    Script Date: 11/23/2024 1:17:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[orders](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[create_time] [datetime] NULL,
	[update_time] [datetime] NULL,
	[ship_fee] [decimal](10, 2) NOT NULL,
	[product_fee] [decimal](10, 2) NOT NULL,
	[total] [decimal](10, 2) NOT NULL,
	[payment_status] [int] NULL,
	[address] [varchar](255) NOT NULL,
	[description] [text] NULL,
	[payment_methodsID] [int] NOT NULL,
	[order_statusID] [int] NOT NULL,
	[customersID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[origins]    Script Date: 11/23/2024 1:17:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[origins](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[payment_methods]    Script Date: 11/23/2024 1:17:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[payment_methods](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product_details]    Script Date: 11/23/2024 1:17:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product_details](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[quantity] [int] NOT NULL,
	[price] [int] NOT NULL,
	[productsID] [int] NOT NULL,
	[sizesID] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[products]    Script Date: 11/23/2024 1:17:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[products](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](255) NOT NULL,
	[image] [varchar](255) NULL,
	[description] [nvarchar](255) NULL,
	[typesID] [int] NOT NULL,
	[originsID] [int] NOT NULL,
	[brandsID] [int] NOT NULL,
	[materialsID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[rules]    Script Date: 11/23/2024 1:17:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[rules](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[sizes]    Script Date: 11/23/2024 1:17:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[sizes](
	[ID] [varchar](255) NOT NULL,
	[name] [nvarchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[staffs]    Script Date: 11/23/2024 1:17:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[staffs](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](255) NOT NULL,
	[phone] [varchar](255) NOT NULL,
	[address] [nvarchar](255) NULL,
	[status] [int] NOT NULL,
	[accountsID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[types]    Script Date: 11/23/2024 1:17:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[types](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[feedbacks] ADD  CONSTRAINT [DF_feedbacks_create_time]  DEFAULT (getdate()) FOR [create_time]
GO
ALTER TABLE [dbo].[orders] ADD  DEFAULT (getdate()) FOR [create_time]
GO
ALTER TABLE [dbo].[orders] ADD  DEFAULT (getdate()) FOR [update_time]
GO
ALTER TABLE [dbo].[accounts]  WITH CHECK ADD  CONSTRAINT [FKaccounts593365] FOREIGN KEY([rulesID])
REFERENCES [dbo].[rules] ([ID])
GO
ALTER TABLE [dbo].[accounts] CHECK CONSTRAINT [FKaccounts593365]
GO
ALTER TABLE [dbo].[carts]  WITH CHECK ADD FOREIGN KEY([customersID])
REFERENCES [dbo].[customers] ([ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[carts]  WITH CHECK ADD FOREIGN KEY([product_detailsID])
REFERENCES [dbo].[product_details] ([ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[customers]  WITH CHECK ADD  CONSTRAINT [FKcustomers898137] FOREIGN KEY([accountsID])
REFERENCES [dbo].[accounts] ([ID])
GO
ALTER TABLE [dbo].[customers] CHECK CONSTRAINT [FKcustomers898137]
GO
ALTER TABLE [dbo].[feedbacks]  WITH CHECK ADD  CONSTRAINT [FKfeedbacks310000] FOREIGN KEY([productsID])
REFERENCES [dbo].[products] ([ID])
GO
ALTER TABLE [dbo].[feedbacks] CHECK CONSTRAINT [FKfeedbacks310000]
GO
ALTER TABLE [dbo].[feedbacks]  WITH CHECK ADD  CONSTRAINT [FKfeedbacks57692] FOREIGN KEY([customersID])
REFERENCES [dbo].[customers] ([ID])
GO
ALTER TABLE [dbo].[feedbacks] CHECK CONSTRAINT [FKfeedbacks57692]
GO
ALTER TABLE [dbo].[order_details]  WITH CHECK ADD FOREIGN KEY([ordersID])
REFERENCES [dbo].[orders] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[order_details]  WITH CHECK ADD FOREIGN KEY([product_detailsID])
REFERENCES [dbo].[product_details] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[orders]  WITH CHECK ADD FOREIGN KEY([order_statusID])
REFERENCES [dbo].[order_status] ([id])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[orders]  WITH CHECK ADD FOREIGN KEY([payment_methodsID])
REFERENCES [dbo].[payment_methods] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[orders]  WITH CHECK ADD  CONSTRAINT [fk_orders_customers] FOREIGN KEY([customersID])
REFERENCES [dbo].[customers] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[orders] CHECK CONSTRAINT [fk_orders_customers]
GO
ALTER TABLE [dbo].[product_details]  WITH CHECK ADD  CONSTRAINT [FKproduct_de141770] FOREIGN KEY([sizesID])
REFERENCES [dbo].[sizes] ([ID])
GO
ALTER TABLE [dbo].[product_details] CHECK CONSTRAINT [FKproduct_de141770]
GO
ALTER TABLE [dbo].[product_details]  WITH CHECK ADD  CONSTRAINT [FKproduct_de392116] FOREIGN KEY([productsID])
REFERENCES [dbo].[products] ([ID])
GO
ALTER TABLE [dbo].[product_details] CHECK CONSTRAINT [FKproduct_de392116]
GO
ALTER TABLE [dbo].[products]  WITH CHECK ADD  CONSTRAINT [FKproducts272884] FOREIGN KEY([materialsID])
REFERENCES [dbo].[materials] ([ID])
GO
ALTER TABLE [dbo].[products] CHECK CONSTRAINT [FKproducts272884]
GO
ALTER TABLE [dbo].[products]  WITH CHECK ADD  CONSTRAINT [FKproducts470421] FOREIGN KEY([originsID])
REFERENCES [dbo].[origins] ([ID])
GO
ALTER TABLE [dbo].[products] CHECK CONSTRAINT [FKproducts470421]
GO
ALTER TABLE [dbo].[products]  WITH CHECK ADD  CONSTRAINT [FKproducts812174] FOREIGN KEY([brandsID])
REFERENCES [dbo].[brands] ([ID])
GO
ALTER TABLE [dbo].[products] CHECK CONSTRAINT [FKproducts812174]
GO
ALTER TABLE [dbo].[products]  WITH CHECK ADD  CONSTRAINT [FKproducts988741] FOREIGN KEY([typesID])
REFERENCES [dbo].[types] ([ID])
GO
ALTER TABLE [dbo].[products] CHECK CONSTRAINT [FKproducts988741]
GO
ALTER TABLE [dbo].[staffs]  WITH CHECK ADD  CONSTRAINT [FKstaffs13411] FOREIGN KEY([accountsID])
REFERENCES [dbo].[accounts] ([ID])
GO
ALTER TABLE [dbo].[staffs] CHECK CONSTRAINT [FKstaffs13411]
GO
/****** Object:  Trigger [dbo].[trg_feedbacks_create_time]    Script Date: 11/23/2024 1:17:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE TRIGGER [dbo].[trg_feedbacks_create_time]
ON [dbo].[feedbacks]
AFTER INSERT
AS
BEGIN
    -- Cập nhật giá trị cho cột create_time nếu cột này không được cung cấp giá trị khi thêm dữ liệu
    UPDATE dbo.feedbacks
    SET dbo.feedbacks.create_time = ISNULL(dbo.feedbacks.create_time, CURRENT_TIMESTAMP)
    FROM dbo.feedbacks
    INNER JOIN inserted ON dbo.feedbacks.id = inserted.id;
END;
GO
ALTER TABLE [dbo].[feedbacks] ENABLE TRIGGER [trg_feedbacks_create_time]
GO
/****** Object:  Trigger [dbo].[trg_orders_create_time]    Script Date: 11/23/2024 1:17:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- Trigger tự động điền giá trị create_time và update_time khi thêm bản ghi mới
CREATE TRIGGER [dbo].[trg_orders_create_time]
ON [dbo].[orders]
AFTER INSERT
AS
BEGIN
    -- Cập nhật cột create_time và update_time cho các bản ghi mới
    UPDATE dbo.orders
    SET dbo.orders.create_time = ISNULL(dbo.orders.create_time, CURRENT_TIMESTAMP),
        dbo.orders.update_time = ISNULL(dbo.orders.update_time, CURRENT_TIMESTAMP)
    FROM dbo.orders
    INNER JOIN inserted ON dbo.orders.id = inserted.id;
END;
GO
ALTER TABLE [dbo].[orders] ENABLE TRIGGER [trg_orders_create_time]
GO
/****** Object:  Trigger [dbo].[trg_orders_update_time]    Script Date: 11/23/2024 1:17:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- Trigger tự động cập nhật update_time khi bản ghi được cập nhật
CREATE TRIGGER [dbo].[trg_orders_update_time]
ON [dbo].[orders]
AFTER UPDATE
AS
BEGIN
    -- Cập nhật cột update_time cho các bản ghi được cập nhật
    UPDATE dbo.orders
    SET dbo.orders.update_time = CURRENT_TIMESTAMP
    FROM dbo.orders
    INNER JOIN inserted ON dbo.orders.id = inserted.id;
END;
GO
ALTER TABLE [dbo].[orders] ENABLE TRIGGER [trg_orders_update_time]
GO
USE [master]
GO
ALTER DATABASE [shop_c] SET  READ_WRITE 
GO