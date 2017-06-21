USE [PAPYROSM3_TEST]
GO

/****** Object:  Table [dbo].[ED_admin_users_passwords]    Script Date: 21/6/2017 2:28:18 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[ED_admin_users_passwords](
	[id] [int] NOT NULL,
	[password] [varchar](255) NOT NULL,
	[owned_by] [int] NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [dbo].[ED_administrative_users]    Script Date: 21/6/2017 2:28:18 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[ED_administrative_users](
	[id] [int] NOT NULL,
	[username] [varchar](255) NOT NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [dbo].[ED_attached_documents_received]    Script Date: 21/6/2017 2:28:18 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[ED_attached_documents_received](
	[id] [int] NOT NULL,
	[actual_document_filepath] [varchar](255) NULL,
	[docId] [int] NULL,
	[reference_document] [int] NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [dbo].[ED_attached_documents_send]    Script Date: 21/6/2017 2:28:18 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[ED_attached_documents_send](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[actual_document_filepath] [varchar](255) NULL,
	[docId] [int] NULL,
	[reference_document] [varchar](255) NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [dbo].[ED_BSD]    Script Date: 21/6/2017 2:28:18 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING OFF
GO

CREATE TABLE [dbo].[ED_BSD](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[message_unique_id] [varchar](255) NOT NULL,
	[sender_id] [int] NOT NULL,
	[receiver_id] [int] NOT NULL,
	[di_standard] [varchar](255) NOT NULL,
	[di_type_version] [varchar](255) NOT NULL,
	[di_id] [varchar](255) NOT NULL,
	[di_type] [varchar](255) NOT NULL,
	[di_creatiom_time] [datetime] NOT NULL,
	[man_type] [varchar](255) NOT NULL,
	[man_uni] [varchar](255) NOT NULL,
	[man_descr] [varchar](255) NOT NULL,
	[man_lang] [varchar](255) NOT NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [dbo].[ED_BSD_HAS_SCOPE]    Script Date: 21/6/2017 2:28:18 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING OFF
GO

CREATE TABLE [dbo].[ED_BSD_HAS_SCOPE](
	[sbd_id] [int] IDENTITY(1,1) NOT NULL,
	[scope_id] [varchar](255) NOT NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [dbo].[ED_BSD_SCOPE]    Script Date: 21/6/2017 2:28:18 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING OFF
GO

CREATE TABLE [dbo].[ED_BSD_SCOPE](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[sc_type] [varchar](255) NOT NULL,
	[sc_id] [varchar](255) NOT NULL,
	[sc_instance] [varchar](500) NOT NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [dbo].[ED_document_status]    Script Date: 21/6/2017 2:28:18 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[ED_document_status](
	[id] [int] NOT NULL,
	[status] [varchar](255) NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [dbo].[ED_documents_received]    Script Date: 21/6/2017 2:28:18 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[ED_documents_received](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[actual_document_filepath] [varchar](255) NULL,
	[docId] [int] NULL,
	[doc_acceptance_period] [datetime] NULL,
	[doc_authority_applicant] [varchar](255) NULL,
	[doc_comments] [varchar](255) NULL,
	[doc_description] [varchar](255) NULL,
	[doc_etiquette_creation_date] [datetime] NULL,
	[doc_issuing_authority] [varchar](255) NULL,
	[doc_issuing_organization] [varchar](255) NULL,
	[doc_language] [varchar](255) NULL,
	[doc_organization_applicant] [varchar](255) NULL,
	[doc_organization_etiquette] [varchar](255) NOT NULL,
	[doc_purpose] [varchar](255) NULL,
	[doc_received_from_ap_date] [datetime] NULL,
	[doc_receiver_authority] [varchar](255) NULL,
	[doc_receiver_organization] [varchar](255) NULL,
	[doc_submited_to_ap_date] [datetime] NULL,
	[doc_title] [varchar](255) NOT NULL,
	[doc_type] [varchar](255) NOT NULL,
	[doc_valid_period] [datetime2](7) NULL,
	[mes_id] [int] NOT NULL,
	[mes_unique_id] [varchar](255) NOT NULL,
	[ref_document_unique_id] [varchar](255) NULL,
	[doc_status] [int] NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [dbo].[ED_documents_send]    Script Date: 21/6/2017 2:28:18 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[ED_documents_send](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[actual_document_filepath] [varchar](255) NULL,
	[docId] [int] NULL,
	[doc_acceptance_period] [datetime] NULL,
	[doc_authority_applicant] [varchar](255) NULL,
	[doc_comments] [varchar](255) NULL,
	[doc_description] [varchar](255) NULL,
	[doc_etiquette_creation_date] [datetime] NULL,
	[doc_issuing_authority] [varchar](255) NULL,
	[doc_issuing_organization] [varchar](255) NULL,
	[doc_language] [varchar](255) NULL,
	[doc_organization_applicant] [varchar](255) NULL,
	[doc_organization_etiquette] [varchar](255) NOT NULL,
	[doc_purpose] [varchar](255) NULL,
	[doc_received_from_ap_date] [datetime] NULL,
	[doc_receiver_authority] [varchar](255) NULL,
	[doc_receiver_organization] [varchar](255) NULL,
	[doc_submited_to_ap_date] [datetime] NULL,
	[doc_title] [varchar](255) NOT NULL,
	[doc_type] [varchar](255) NOT NULL,
	[doc_valid_period] [datetime] NULL,
	[mes_id] [int] NOT NULL,
	[mes_unique_id] [varchar](255) NOT NULL,
	[ref_document_unique_id] [varchar](255) NULL,
	[doc_status] [int] NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [dbo].[ED_evidence_documents_send]    Script Date: 21/6/2017 2:28:18 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[ED_evidence_documents_send](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[actual_document_filepath] [varchar](255) NULL,
	[docId] [int] NULL,
	[reference_document] [varchar](255) NULL,
	[eventcode] [varchar](255) NULL,
	[evidence_identifier] [varchar](255) NULL,
	[evidence_name] [varchar](255) NULL,
	[evidence_time] [varchar](255) NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [dbo].[ED_message_received_from_ap]    Script Date: 21/6/2017 2:28:18 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[ED_message_received_from_ap](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[message_unique_id] [varchar](255) NOT NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [dbo].[ED_message_send_to_ap]    Script Date: 21/6/2017 2:28:18 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[ED_message_send_to_ap](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[message_unique_id] [varchar](255) NOT NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [dbo].[ED_Participants]    Script Date: 21/6/2017 2:28:18 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[ED_Participants](
	[id] [int] NOT NULL,
	[participant_identifier_scheme] [varchar](255) NOT NULL,
	[participant_identifier_value] [varchar](255) NOT NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO


