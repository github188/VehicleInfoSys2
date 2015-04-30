;============================================================
#define company ""
#define GroupName "��Ƶ�������ϵͳ"
#define myappname "������Ϣϵͳ"
#define MYAPPDIR   "D:\VideoInvestigation\car"
#define SourcePath ".\"             
#define ParentPath "..\"
#define tomcat "apache-tomcat-6.0.39"
#define mysql "mysql-5.5.37-win32"
#define service_name "tomcat_car"

#define extname "_Setup"
#define installername myappname+"_"+GetDateTimeString('yyyy-MM-dd','','')+"_"
#define ver "2.0.1.7"   

[Setup]
AppName={#company}{#myappname}
AppVersion={#ver}
DefaultDirName={#MYAPPDIR}
DefaultGroupName={#company}{#GroupName}                                                              
OutputDir=output
OutputBaseFilename={#company}{#installername}{#ver}{#extname}
Compression=lzma
SolidCompression=yes                                                 
AlwaysRestart=yes
ShowLanguageDialog=Yes
SetupLogging=yes                                                     

DisableWelcomePage=no
DirExistsWarning=no
DisableReadyPage=yes
DisableDirPage=no                                              
DisableProgramGroupPage=yes 

VersionInfoTextVersion={#ver}
VersionInfoVersion={#ver}
PrivilegesRequired=admin                                                           

[Languages]
Name: "chinesesimp"; MessagesFile: "compiler:Default.isl"

[Files]
Source: "{#SourcePath}\{#tomcat}\*" ;              DestDir: "{app}\tomcat\";Components: main; Flags: recursesubdirs createallsubdirs uninsneveruninstall
Source: "{#SourcePath}\{#mysql}\*" ;               DestDir: "{app}\mysql\";Components: db\mysql_self; Flags: recursesubdirs createallsubdirs uninsneveruninstall
Source: "{#SourcePath}\{#mysql}\bin\mysql.exe" ;   DestDir: "{app}\mysql\bin\";Components: db\mysql_other; Flags:uninsneveruninstall
Source: "{#SourcePath}\Gis_0001\*" ;               DestDir: "{app}\Gis_0001\";Components: main; Flags: recursesubdirs createallsubdirs uninsneveruninstall
Source: "{#SourcePath}\jre\*" ;                    DestDir: "{app}\jre\";Components: main; Flags: recursesubdirs createallsubdirs uninsneveruninstall
Source: "{#SourcePath}\GetLocalIP\*" ;             DestDir: "{app}\GetLocalIP\";Components: main; Flags: ignoreversion
Source: "{#SourcePath}\db\*";                      DestDir: "{app}\db\";Components: main; Flags: ignoreversion
Source: "{#SourcePath}\service\*" ;                DestDir: "{app}\service\";Components: main; Flags: ignoreversion
Source: "{#SourcePath}\script\*" ;                 DestDir: "{app}\script\";Components: main; Flags: ignoreversion
Source: "{#SourcePath}\server\*" ;                 DestDir: "{app}\server\";Components: main; Flags: recursesubdirs createallsubdirs uninsneveruninstall
Source: "{#SourcePath}\slaver\*" ;                 DestDir: "{app}\slaver\";Components: main; Flags: recursesubdirs createallsubdirs uninsneveruninstall
Source: "{#SourcePath}\StreamServer\*";            DestDir: "{app}\StreamServer\";Components: main; Flags: ignoreversion solidbreak uninsrestartdelete

[Components]
Name: "main"; Description: "��Ҫ�ļ�"; Types: full custom; Flags: fixed  
Name: "db"; Description: "ѡ�����ݿ�"; Types: full custom;Flags: fixed 
Name: "db\mysql_self"; Description: "ʹ���Դ���mysql"; Types: full custom;Flags: exclusive
Name: "db\mysql_other"; Description: "ʹ��ͼ��ϵͳ��mysql"; Types: custom;Flags: exclusive

[Types]
Name: "full"; Description: "��ȫ��װ"
Name: "custom"; Description: "�Զ��尲װ"; Flags: iscustom

[Dirs]
Name: "{app}\vlpr_result"
 
[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"

[Icons]                                       
Name: "{commondesktop}\{#company}{#myappname}"; Filename:"{pf32}\Internet Explorer\iexplore.exe"; Parameters:"http://localhost:8081";
Name: "{group}\{#myappname}\����{#company}{#myappname}"; Filename:"{app}\script\startTomcat.bat"
Name: "{group}\{#myappname}\ж��";                 Filename: "{app}\unins000.exe"                     

[code] 
// ExpandConstant��д �����»������
function _(str: String):String;
begin
    Result:=ExpandConstant(str);
end;

type
    SERVICE_STATUS = record
    dwServiceType              : cardinal;
    dwCurrentState             : cardinal;
    dwControlsAccepted         : cardinal;
    dwWin32ExitCode            : cardinal;
    dwServiceSpecificExitCode  : cardinal;
    dwCheckPoint               : cardinal;
    dwWaitHint                 : cardinal;
end;
    HANDLE = cardinal;

const
    SERVICE_QUERY_CONFIG         = $1;
    SERVICE_CHANGE_CONFIG        = $2;
    SERVICE_QUERY_STATUS         = $4;
    SERVICE_START                = $10;
    SERVICE_STOP                 = $20;
    SERVICE_ALL_ACCESS           = $f01ff;
    SC_MANAGER_ALL_ACCESS        = $f003f;
    SERVICE_WIN32_OWN_PROCESS    = $10;
    SERVICE_WIN32_SHARE_PROCESS  = $20;
    SERVICE_WIN32                = $30;
    SERVICE_INTERACTIVE_PROCESS  = $100;
    SERVICE_BOOT_START           = $0;
    SERVICE_SYSTEM_START         = $1;
    SERVICE_AUTO_START           = $2;
    SERVICE_DEMAND_START         = $3;
    SERVICE_DISABLED             = $4;
    SERVICE_DELETE               = $10000;
    SERVICE_CONTROL_STOP         = $1;
    SERVICE_CONTROL_PAUSE        = $2;
    SERVICE_CONTROL_CONTINUE     = $3;
    SERVICE_CONTROL_INTERROGATE  = $4;
    SERVICE_STOPPED              = $1;
    SERVICE_START_PENDING        = $2;
    SERVICE_STOP_PENDING         = $3;
    SERVICE_RUNNING              = $4;
    SERVICE_CONTINUE_PENDING     = $5;
    SERVICE_PAUSE_PENDING        = $6;
    SERVICE_PAUSED               = $7;

// #######################################################################################
// nt based service utilities
// #######################################################################################
function OpenSCManager(lpMachineName, lpDatabaseName: string; dwDesiredAccess :cardinal): HANDLE;
external 'OpenSCManagerA@advapi32.dll stdcall';

function OpenService(hSCManager :HANDLE;lpServiceName: string; dwDesiredAccess :cardinal): HANDLE;
external 'OpenServiceA@advapi32.dll stdcall';

function CloseServiceHandle(hSCObject :HANDLE): boolean;
external 'CloseServiceHandle@advapi32.dll stdcall';

function CreateService(hSCManager :HANDLE;lpServiceName, lpDisplayName: string;dwDesiredAccess,dwServiceType,dwStartType,dwErrorControl: cardinal;lpBinaryPathName,lpLoadOrderGroup: String; lpdwTagId : cardinal;lpDependencies,lpServiceStartName,lpPassword :string): cardinal;
external 'CreateServiceA@advapi32.dll stdcall';

function DeleteService(hService :HANDLE): boolean;
external 'DeleteService@advapi32.dll stdcall';

function StartNTService(hService :HANDLE;dwNumServiceArgs : cardinal;lpServiceArgVectors : cardinal) : boolean;
external 'StartServiceA@advapi32.dll stdcall';

function ControlService(hService :HANDLE; dwControl :cardinal;var ServiceStatus :SERVICE_STATUS) : boolean;
external 'ControlService@advapi32.dll stdcall';

function QueryServiceStatus(hService :HANDLE;var ServiceStatus :SERVICE_STATUS) : boolean;
external 'QueryServiceStatus@advapi32.dll stdcall';

function QueryServiceStatusEx(hService :HANDLE;ServiceStatus :SERVICE_STATUS) : boolean;
external 'QueryServiceStatus@advapi32.dll stdcall';

///////////////////////////////////////////////////////////////////////////////////////////////

function OpenServiceManager() : HANDLE;
begin
  if UsingWinNT() = true then 
    begin
      Result := OpenSCManager('','ServicesActive',SC_MANAGER_ALL_ACCESS);
      if Result = 0 then
        MsgBox('the servicemanager is not available', mbError, MB_OK)
    end
  else 
    begin
      MsgBox('only nt based systems support services', mbError, MB_OK)
      Result := 0;
    end
end;

function IsServiceInstalled(ServiceName: string) : boolean;
var
  hSCM    : HANDLE;
  hService: HANDLE;
begin
  hSCM := OpenServiceManager();
  Result := false;
  if hSCM <> 0 then 
  begin
    hService := OpenService(hSCM,ServiceName,SERVICE_QUERY_CONFIG);
    if hService <> 0 then 
    begin
      Result := true;
      CloseServiceHandle(hService)
    end;
    CloseServiceHandle(hSCM)
  end
end;

function InstallService(FileName, ServiceName, DisplayName, Description : string;ServiceType,StartType :cardinal) : boolean;
var
  hSCM    : HANDLE;
  hService: HANDLE;
begin
  hSCM := OpenServiceManager();
  Result := false;
  if hSCM <> 0 then 
  begin
    hService := CreateService(hSCM,ServiceName,DisplayName,SERVICE_ALL_ACCESS,ServiceType,StartType,0,FileName,'',0,'','','');
    if hService <> 0 then 
    begin
      Result := true;
      // Win2K & WinXP supports aditional description text for services
      if Description<> '' then
        RegWriteStringValue(HKLM,'System\CurrentControlSet\Services\' + ServiceName,'Description',Description);
        CloseServiceHandle(hService)
    end;
    CloseServiceHandle(hSCM)
  end
end;

function RemoveService(ServiceName: string) : boolean;
var
  hSCM    : HANDLE;
  hService: HANDLE;
begin
  hSCM := OpenServiceManager();
  Result := false;
  if hSCM <> 0 then 
  begin
    hService := OpenService(hSCM,ServiceName,SERVICE_DELETE);
    if hService <> 0 then 
    begin
      Result := DeleteService(hService);
      CloseServiceHandle(hService)
    end;
    CloseServiceHandle(hSCM)
  end
end;

function StartService(ServiceName: string) : boolean;
var
  hSCM    : HANDLE;
  hService: HANDLE;
begin
  hSCM := OpenServiceManager();
  Result := false;
  if hSCM <> 0 then 
  begin
    hService := OpenService(hSCM,ServiceName,SERVICE_START);
    if hService <> 0 then 
    begin
      Result := StartNTService(hService,0,0);
      CloseServiceHandle(hService)
    end;
    CloseServiceHandle(hSCM)
  end;
end;

function StopService(ServiceName: string) : boolean;
var
  hSCM    : HANDLE;
  hService: HANDLE;
  Status  : SERVICE_STATUS;
begin
  hSCM := OpenServiceManager();
  Result := false;
  if hSCM <> 0 then 
  begin
    hService := OpenService(hSCM,ServiceName,SERVICE_STOP);
    if hService <> 0 then 
    begin
      Result := ControlService(hService,SERVICE_CONTROL_STOP,Status);
      CloseServiceHandle(hService)
    end;
    CloseServiceHandle(hSCM)
  end;
end;

function IsServiceRunning(ServiceName: string) : boolean;
var
  hSCM    : HANDLE;
  hService: HANDLE;
  Status  : SERVICE_STATUS;
begin
  hSCM := OpenServiceManager();
  Result := false;
  if hSCM <> 0 then 
  begin
    hService := OpenService(hSCM,ServiceName,SERVICE_QUERY_STATUS);
    if hService <> 0 then 
    begin
      if QueryServiceStatus(hService,Status) then 
      begin
        Result :=(Status.dwCurrentState = SERVICE_RUNNING)
      end;
      CloseServiceHandle(hService)
    end;
    CloseServiceHandle(hSCM)
  end
end;


procedure SetEnv(aEnvName, aEnvValue: string; aIsInstall: Boolean);
var
  sOrgValue: string;
  x,len: integer;
begin
  RegQueryStringValue(HKEY_LOCAL_MACHINE, 'SYSTEM\ControlSet001\Control\Session Manager\Environment', aEnvName, sOrgValue)
  sOrgValue := Trim(sOrgValue);
  begin
    x := pos( Uppercase(aEnvValue),Uppercase(sOrgValue));
    len := length(aEnvValue);
    if aIsInstall then
      begin
        if length(sOrgValue)>0 then 
          aEnvValue := ';'+ aEnvValue;
        if x = 0 then 
          Insert(aEnvValue,sOrgValue,length(sOrgValue) +1);
      end
    else
      begin
        if x>0 then 
          Delete(sOrgValue,x,len);
        if length(sOrgValue)=0 then
        begin
          RegDeleteValue(HKEY_LOCAL_MACHINE, 'SYSTEM\ControlSet001\Control\Session Manager\Environment',aEnvName);
          exit;
        end;
      end;
      StringChange(sOrgValue,';;',';');
      RegWriteStringValue(HKEY_LOCAL_MACHINE, 'SYSTEM\ControlSet001\Control\Session Manager\Environment', aEnvName, sOrgValue)
  end;
end;

procedure ModifyConfigFiles(IniFile, OldString, NewString: string);
var
  i: Integer;
  IniFileLines: TArrayOfString;
begin
  LoadStringsFromFile(IniFile, IniFileLines);
  for i:= 0 to GetArrayLength(IniFileLines)-1 do
  begin
    if (Pos(OldString, IniFileLines[i]) > 0) then
    StringChange(IniFileLines[i], OldString, NewString);
    SaveStringsToFile(IniFile, IniFileLines, False);
  end;
end;

procedure ModifyProperties(IniFile, OldString, NewString: string);
var
  i: Integer;
  IniFileLines: TArrayOfString;
begin
  LoadStringsFromFile(IniFile, IniFileLines);
  for i:= 0 to GetArrayLength(IniFileLines)-1 do
  begin
    if (Pos(OldString, IniFileLines[i]) > 0) then
    IniFileLines[i] := NewString;
    SaveStringsToFile(IniFile, IniFileLines, False);
  end;
end;

procedure UninsService(ServiceName:String);
var                                                                                                  
ErrorCode: Integer;
begin
  ServiceName:=_('"'+ServiceName+'"');
  ShellExec('open', 'net.exe', _('stop ' + ServiceName), '', SW_HIDE, ewWaitUntilTerminated, ErrorCode);
  ShellExec('open', 'sc.exe', _('delete ' + ServiceName), '', SW_HIDE, ewWaitUntilTerminated, ErrorCode);
end;

//Custom Page
var
  DataDirPage: TInputDirWizardPage;
  ServerIpPage: TInputQueryWizardPage;
  DataDirPageID, ServerIpPageID, Page1ID, CheckPageID: Integer;
  FtpPathValue: String;
  Page, CheckPage: TWizardPage;
  Edit, Edit2: TNewEdit;
  CheckBox: TCheckBox;
  Label1, Label2: TLabel;

procedure InitializeWizard();  
begin
 // ServerIpPage := CreateInputQueryPage(wpSelectDir, '�Զ��尸������Ƶ���������IP', '��������Ƶ����������Ҫ�����������ύ��������,������д�����������IP', '����д���������IP,Ȼ�󵥻���һ��');
  //ServerIpPageID := ServerIpPage.ID;  
 // ServerIpPage.Add('���������IP��',false);
end;

function GetDataDir(Param: String): String;
begin
  Result := DataDirPage.Values[0];
end; 

procedure CurStepChanged(CurStep: TSetupStep);
var
  LocalIp: AnsiString;
  ErrorCode, i: Integer;
  strReplay: String;
  serverIp: String;
  basedir: String;
  datadir: String;
  bFlag:Boolean;
begin 
if CurStep = ssPostInstall then
	begin
                    
  //����ǽ����
   WizardForm.StatusLabel.Caption := '��ӷ���ǽ����...';
  ShellExec('open', 'netsh.exe', _('advfirewall firewall add rule name = "java" dir = in program = "{app}\jre\bin\java.exe" action = allow'), '', SW_HIDE, ewWaitUntilTerminated, ErrorCode);
  
  //���ѡ����ʹ���Դ���mysql�������Լ���װmysql
  if IsComponentSelected('db\mysql_self') then
  begin
  basedir:=_('basedir={app}\mysql');
  datadir:=_('datadir={app}\mysql\data');
  StringChange(basedir,'\','/');
  StringChange(datadir,'\','/');
   //�滻mysql���������             
   ModifyProperties( _('{app}\mysql\my.ini'), 'basedir', basedir);
   ModifyProperties( _('{app}\mysql\my.ini'), 'datadir', datadir);
   ModifyProperties( _('{app}\mysql\my.ini'), 'port=', 'port=3306');
   //����mysql
   WizardForm.StatusLabel.Caption := '����mysql...';
   ShellExec('open', _('{app}\script\startMysql.bat'), _('{app}\mysql'), _('{app}\'), SW_HIDE, ewWaitUntilTerminated, ErrorCode);  
   end;

  //���ѡ����ʹ��ͼ���mysql ,����ֱ�ӵ���������
  if IsComponentSelected('db\mysql_other') then
  begin
    ShellExec('open', _('{app}\script\initData.bat'), _('{app}\mysql'), _('{app}\'), SW_HIDE, ewWaitUntilTerminated, ErrorCode);
  end;
	                   
    //����gis�ͽ��������
    ModifyProperties( _('{app}\tomcat\conf\server.xml'), 'path="/Gis_0001"', _('<Context docBase="{app}\Gis_0001" path="/Gis_0001" ></Context>'));
    ModifyProperties( _('{app}\tomcat\conf\server.xml'), 'path="/vlpr_result"', _('<Context docBase="{app}\vlpr_result" path="/vlpr_result" ></Context>'));

    ModifyConfigFiles( _('{app}\tomcat\webapps\ROOT\WEB-INF\classes\parameter.properties'), '\\\\','\\');

    	//ȡ�ñ���ip
    ShellExec('open', _('{app}\GetLocalIP\GetLocalIP.exe'), '', '', SW_HIDE, ewWaitUntilTerminated, ErrorCode);
    bFlag := LoadStringFromFile(_('{app}\GetLocalIP\ip.txt'), LocalIp);
    if bFlag = false then
        LocalIp := '127.0.0.1';
    
    //�޸������ļ�ֵ
    ModifyProperties( _('{app}\tomcat\webapps\ROOT\WEB-INF\classes\parameter.properties'), 'serverIp','serverIp='+LocalIp);
    ModifyProperties( _('{app}\tomcat\webapps\ROOT\WEB-INF\classes\parameter.properties'), 'PictureServerHost','PictureServerHost=http://'+LocalIp+':8081');
    ModifyProperties( _('{app}\tomcat\webapps\ROOT\WEB-INF\classes\parameter.properties'), 'activemq.brokerUrl','activemq.brokerUrl=tcp://'+LocalIp+':6616');
    ModifyProperties( _('{app}\slaver\run.bat'), 'activemqIp=','set activemqIp='+LocalIp);
   	  

    //��װ��ý�����
        WizardForm.StatusLabel.Caption := '��װ��ý�����...';
        ShellExec('open', _('{app}\StreamServer\AnaStreamingService.exe'), '-i',_('{app}\StreamServer\'), SW_HIDE, ewWaitUntilTerminated, ErrorCode);
	      ShellExec('open', _('{app}\StreamServer\AnaStreamingService.exe'), '-w -i',_('{app}\StreamServer\'), SW_HIDE, ewWaitUntilTerminated, ErrorCode); 
        WizardForm.StatusLabel.Caption := '������ý�����...';
        ShellExec('open', _('{app}\StreamServer\AnaStreamingService.exe'), '-s', _('{app}\StreamServer\'), SW_HIDE, ewWaitUntilTerminated, ErrorCode);
        ShellExec('open', _('{app}\StreamServer\AnaStreamingService.exe'), '-w -s',_('{app}\StreamServer\'), SW_HIDE, ewWaitUntilTerminated, ErrorCode);

    //����tomcat������
    WizardForm.StatusLabel.Caption := '����tomcat����...';
    RegWriteStringValue(HKEY_LOCAL_MACHINE, _('SYSTEM\CurrentControlSet\services\{#service_name}\Parameters'),'Application',_('{app}\script\startTomcat.bat')); 
    ShellExec('open', _('{app}\service\instsrv.exe'), _('{#service_name} {app}\service\srvany.exe'), '', SW_HIDE, ewWaitUntilTerminated, ErrorCode);
   
    WizardForm.StatusLabel.Caption := '����tomcat����...';
    ShellExec('open', 'net', _('start {#service_name}'),'', SW_HIDE, ewWaitUntilTerminated, ErrorCode);

    DelTree(_('{app}\GetLocalIP'),true,true,true);
	end;
end; 

procedure CurUninstallStepChanged(CurUninstallStep: TUninstallStep);
var
  ErrorCode: Integer;
begin
    if CurUninstallStep=usUninstall then
    begin
      UninsService('{#service_name}');
      UninsService('Mysql_car');
      UninsService('AnaStreamingService Watchdog');
      UninsService('AnaStreamingService');

      ShellExec('open', _('{app}\script\stopTomcat.bat'), '', '', SW_HIDE, ewWaitUntilTerminated, ErrorCode);
      ShellExec('open', _('{app}\script\kill.bat'), '', '', SW_HIDE, ewWaitUntilTerminated, ErrorCode);
    end;
    if CurUninstallStep=usDone then
    begin
      DelTree(ExpandConstant('{app}'), True, True, True);
    end;
end;
