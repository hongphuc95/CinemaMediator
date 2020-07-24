create table PROJECT.Film
(
	ID bigint not null
		primary key,
	Movie varchar(255) null,
	ReleaseDate date null,
	Genre varchar(20) null,
	Distributor varchar(20) null,
	ProductionBudget varchar(20) null,
	DomesticGross varchar(20) null,
	WorldwideGross varchar(20) null
)
;


