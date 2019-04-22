settings = NewSettings()
settings.link.libs:Add("lua")

if platform == "macosx" then
	settings.cc.includes:Add("/usr/local/Cellar/lua/5.3.5_1/include/lua5.3")
end

source = Collect("src/*.cpp")
objects = Compile(settings, source)
exe = Link(settings, "main", objects)