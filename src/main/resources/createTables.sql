USE [edeliveryserver]
GO

/****** Object:  Table [edeliveryserver].[administrative_users]    Script Date: 8/6/2017 5:27:58 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [edeliveryserver].[administrative_users](
	[id] [int] NOT NULL,
	[username] [varchar](255) NOT NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [edeliveryserver].[administrative_users_passwords]    Script Date: 8/6/2017 5:27:58 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [edeliveryserver].[administrative_users_passwords](
	[id] [int] NOT NULL,
	[password] [varchar](255) NOT NULL,
	[owned_by] [int] NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [edeliveryserver].[attached_documents_received]    Script Date: 8/6/2017 5:27:58 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [edeliveryserver].[attached_documents_received](
	[id] [int] NOT NULL,
	[actual_document_filepath] [varchar](255) NULL,
	[docId] [int] NULL,
	[reference_document] [int] NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [edeliveryserver].[attached_documents_send]    Script Date: 8/6/2017 5:27:58 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [edeliveryserver].[attached_documents_send](
	[id] [int] NOT NULL,
	[actual_document_filepath] [varchar](255) NULL,
	[docId] [int] NULL,
	[reference_document] [int] NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [edeliveryserver].[document_status]    Script Date: 8/6/2017 5:27:58 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [edeliveryserver].[document_status](
	[id] [int] NOT NULL,
	[status] [varchar](255) NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [edeliveryserver].[documents_received]    Script Date: 8/6/2017 5:27:58 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [edeliveryserver].[documents_received](
	[id] [int] NOT NULL,
	[actual_document_filepath] [varchar](255) NULL,
	[docId] [int] NULL,
	[document_acceptance_period] [datetime2](7) NULL,
	[document_authority_applicant] [varchar](255) NULL,
	[document_comments] [varchar](255) NULL,
	[document_description] [varchar](255) NULL,
	[document_etiquette_creation_date] [datetime2](7) NULL,
	[document_issuing_authority] [varchar](255) NULL,
	[document_issuing_organization] [varchar](255) NULL,
	[document_language] [varchar](255) NULL,
	[document_organization_applicant] [varchar](255) NULL,
	[document_organization_etiquette] [varchar](255) NOT NULL,
	[document_purpose] [varchar](255) NULL,
	[document_received_from_ap_date] [datetime2](7) NULL,
	[document_receiver_authority] [varchar](255) NULL,
	[document_receiver_organization] [varchar](255) NULL,
	[document_submited_to_ap_date] [datetime2](7) NULL,
	[document_title] [varchar](255) NOT NULL,
	[document_type] [varchar](255) NOT NULL,
	[document_valid_period] [datetime2](7) NULL,
	[message_id] [int] NOT NULL,
	[message_unique_id] [varchar](255) NOT NULL,
	[referenced_document_unique_id] [varchar](255) NULL,
	[document_status] [int] NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [edeliveryserver].[documents_send]    Script Date: 8/6/2017 5:27:58 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [edeliveryserver].[documents_send](
	[id] [int] IDENTITY(1,1) PRIMARY KEY,
	[actual_document_filepath] [varchar](255) NULL,
	[docId] [int] NULL,
	[document_acceptance_period] [datetime2](7) NULL,
	[document_authority_applicant] [varchar](255) NULL,
	[document_comments] [varchar](255) NULL,
	[document_description] [varchar](255) NULL,
	[document_etiquette_creation_date] [datetime2](7) NULL,
	[document_issuing_authority] [varchar](255) NULL,
	[document_issuing_organization] [varchar](255) NULL,
	[document_language] [varchar](255) NULL,
	[document_organization_applicant] [varchar](255) NULL,
	[document_organization_etiquette] [varchar](255) NOT NULL,
	[document_purpose] [varchar](255) NULL,
	[document_received_from_ap_date] [datetime2](7) NULL,
	[document_receiver_authority] [varchar](255) NULL,
	[document_receiver_organization] [varchar](255) NULL,
	[document_submited_to_ap_date] [datetime2](7) NULL,
	[document_title] [varchar](255) NOT NULL,
	[document_type] [varchar](255) NOT NULL,
	[document_valid_period] [datetime2](7) NULL,
	[message_id] [int] NOT NULL,
	[message_unique_id] [varchar](255) NOT NULL,
	[referenced_document_unique_id] [varchar](255) NULL,
	[document_status] [int] NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [edeliveryserver].[message_received_from_ap]    Script Date: 8/6/2017 5:27:58 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [edeliveryserver].[message_received_from_ap](
	[id] [int] NOT NULL,
	[message_unique_id] [varchar](255) NOT NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [edeliveryserver].[message_send_to_ap]    Script Date: 8/6/2017 5:27:58 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [edeliveryserver].[message_send_to_ap](
	[id] [int] NOT NULL,
	[message_unique_id] [varchar](255) NOT NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [edeliveryserver].[Participants]    Script Date: 8/6/2017 5:27:58 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [edeliveryserver].[Participants](
	[id] [int] NOT NULL,
	[participant_identifier_scheme] [varchar](255) NOT NULL,
	[participant_identifier_value] [varchar](255) NOT NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


