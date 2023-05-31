CREATE TABLE [dbo].[Users] (
    [UserId] VARCHAR (50) NOT NULL,
    CONSTRAINT [PK_Users] PRIMARY KEY CLUSTERED ([UserId] ASC)
);

CREATE TABLE [dbo].[Restaurants] (
    [RestaurantId]           BIGINT       NOT NULL,
    [Name]                   VARCHAR (50) NOT NULL,
    [Address_City]           VARCHAR (50) NOT NULL,
    [Address_Street]         VARCHAR (50) NOT NULL,
    [Address_BuildingNumber] VARCHAR (50) NOT NULL,
    [Address_FlatNumber]     VARCHAR (50) NULL,
    [PhoneNumber]            VARCHAR (50) NULL,
    [IsApproved]             BIT          NOT NULL,
    CONSTRAINT [PK_Restaurants] PRIMARY KEY CLUSTERED ([RestaurantId] ASC)
);

CREATE TABLE [dbo].[MenuItems] (
    [MenuItemId]        BIGINT          NOT NULL,
    [RestaurantId]      BIGINT          NOT NULL,
    [Name]              VARCHAR (50)    NOT NULL,
    [Description]       VARCHAR (MAX)   NOT NULL,
    [Price]             DECIMAL (10, 2) NOT NULL,
    [PendingUserId]     VARCHAR (50)    NULL,
    [AcceptedUserId]    VARCHAR (50)    NULL,
    [AcceptedDate]      DATETIME        NULL,
    [CollectedDate]     DATETIME        NULL,
    [AvailableFromDate] DATETIME        NOT NULL,
    CONSTRAINT [PK_MenuItems] PRIMARY KEY CLUSTERED ([MenuItemId] ASC),
    CONSTRAINT [FK_MenuItems_Restaurants] FOREIGN KEY ([RestaurantId]) REFERENCES [dbo].[Restaurants] ([RestaurantId])
);
