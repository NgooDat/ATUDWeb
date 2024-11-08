USE [master]
GO
/****** Object:  Database [shop_c]    Script Date: 11/3/2024 11:03:49 PM ******/
CREATE DATABASE [shop_c]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'shop_c', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\shop_c.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'shop_c_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.SQLEXPRESS\MSSQL\DATA\shop_c_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [shop_c] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [shop_c].[dbo].[sp_fulltext_database] @action = 'enable'
end
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
/****** Object:  Table [dbo].[accounts]    Script Date: 11/3/2024 11:03:49 PM ******/
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
/****** Object:  Table [dbo].[brands]    Script Date: 11/3/2024 11:03:50 PM ******/
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
/****** Object:  Table [dbo].[Carts]    Script Date: 11/3/2024 11:03:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Carts](
	[quantity] [int] NULL,
	[status] [int] NULL,
	[customersID] [int] NOT NULL,
	[product_detailsID] [int] NOT NULL,
	[total_price] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[customersID] ASC,
	[product_detailsID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[customers]    Script Date: 11/3/2024 11:03:50 PM ******/
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
/****** Object:  Table [dbo].[feedbacks]    Script Date: 11/3/2024 11:03:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[feedbacks](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[description] [nvarchar](255) NULL,
	[customersID] [int] NOT NULL,
	[productsID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[invoice_details]    Script Date: 11/3/2024 11:03:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[invoice_details](
	[quantity] [int] NULL,
	[unit_price] [int] NULL,
	[product_detailsID] [int] NOT NULL,
	[invoicesID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[product_detailsID] ASC,
	[invoicesID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[invoice_status]    Script Date: 11/3/2024 11:03:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[invoice_status](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[invoices]    Script Date: 11/3/2024 11:03:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[invoices](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[c_time] [datetime] NOT NULL,
	[ship_fee] [int] NOT NULL,
	[product_fee] [int] NOT NULL,
	[total] [int] NOT NULL,
	[payment_status] [int] NOT NULL,
	[address] [nvarchar](255) NOT NULL,
	[description] [nvarchar](255) NULL,
	[payment_methodsID] [int] NOT NULL,
	[invoice_statusID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[materials]    Script Date: 11/3/2024 11:03:50 PM ******/
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
/****** Object:  Table [dbo].[origins]    Script Date: 11/3/2024 11:03:50 PM ******/
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
/****** Object:  Table [dbo].[payment_methods]    Script Date: 11/3/2024 11:03:50 PM ******/
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
/****** Object:  Table [dbo].[product_details]    Script Date: 11/3/2024 11:03:50 PM ******/
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
/****** Object:  Table [dbo].[products]    Script Date: 11/3/2024 11:03:50 PM ******/
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
/****** Object:  Table [dbo].[rules]    Script Date: 11/3/2024 11:03:50 PM ******/
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
/****** Object:  Table [dbo].[sizes]    Script Date: 11/3/2024 11:03:50 PM ******/
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
/****** Object:  Table [dbo].[staffs]    Script Date: 11/3/2024 11:03:50 PM ******/
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
/****** Object:  Table [dbo].[types]    Script Date: 11/3/2024 11:03:50 PM ******/
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
ALTER TABLE [dbo].[accounts]  WITH CHECK ADD  CONSTRAINT [FKaccounts593365] FOREIGN KEY([rulesID])
REFERENCES [dbo].[rules] ([ID])
GO
ALTER TABLE [dbo].[accounts] CHECK CONSTRAINT [FKaccounts593365]
GO
ALTER TABLE [dbo].[Carts]  WITH CHECK ADD  CONSTRAINT [FKCarts279489] FOREIGN KEY([product_detailsID])
REFERENCES [dbo].[product_details] ([ID])
GO
ALTER TABLE [dbo].[Carts] CHECK CONSTRAINT [FKCarts279489]
GO
ALTER TABLE [dbo].[Carts]  WITH CHECK ADD  CONSTRAINT [FKCarts600012] FOREIGN KEY([customersID])
REFERENCES [dbo].[customers] ([ID])
GO
ALTER TABLE [dbo].[Carts] CHECK CONSTRAINT [FKCarts600012]
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
ALTER TABLE [dbo].[invoice_details]  WITH CHECK ADD  CONSTRAINT [FKinvoice_de344113] FOREIGN KEY([invoicesID])
REFERENCES [dbo].[invoices] ([ID])
GO
ALTER TABLE [dbo].[invoice_details] CHECK CONSTRAINT [FKinvoice_de344113]
GO
ALTER TABLE [dbo].[invoice_details]  WITH CHECK ADD  CONSTRAINT [FKinvoice_de45910] FOREIGN KEY([product_detailsID])
REFERENCES [dbo].[product_details] ([ID])
GO
ALTER TABLE [dbo].[invoice_details] CHECK CONSTRAINT [FKinvoice_de45910]
GO
ALTER TABLE [dbo].[invoices]  WITH CHECK ADD  CONSTRAINT [FKinvoices65901] FOREIGN KEY([invoice_statusID])
REFERENCES [dbo].[invoice_status] ([ID])
GO
ALTER TABLE [dbo].[invoices] CHECK CONSTRAINT [FKinvoices65901]
GO
ALTER TABLE [dbo].[invoices]  WITH CHECK ADD  CONSTRAINT [FKinvoices908559] FOREIGN KEY([payment_methodsID])
REFERENCES [dbo].[payment_methods] ([ID])
GO
ALTER TABLE [dbo].[invoices] CHECK CONSTRAINT [FKinvoices908559]
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
/****** Object:  Trigger [dbo].[trg_calculate_total_price]    Script Date: 11/3/2024 11:03:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TRIGGER [dbo].[trg_calculate_total_price]
ON [dbo].[Carts]
AFTER INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    UPDATE Carts
    SET total_price = Carts.quantity * pd.price
    FROM Carts
    JOIN product_details pd ON Carts.product_detailsID = pd.ID
    WHERE EXISTS (
        SELECT 1
        FROM inserted i
        WHERE i.customersID = Carts.customersID
          AND i.product_detailsID = Carts.product_detailsID
    );
END;
GO
ALTER TABLE [dbo].[Carts] ENABLE TRIGGER [trg_calculate_total_price]
GO
/****** Object:  Trigger [dbo].[trg_calculate_total]    Script Date: 11/3/2024 11:03:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE TRIGGER [dbo].[trg_calculate_total]
ON [dbo].[invoices]
AFTER INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    UPDATE invoices
    SET total = product_fee + ship_fee
    FROM invoices
    WHERE EXISTS (
        SELECT 1
        FROM inserted i
        WHERE i.ID = invoices.ID
    );
END;
GO
ALTER TABLE [dbo].[invoices] ENABLE TRIGGER [trg_calculate_total]
GO
USE [master]
GO
ALTER DATABASE [shop_c] SET  READ_WRITE 
GO
