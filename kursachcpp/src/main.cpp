#include <iostream>

extern "C" {
    #include "lua.h"
    #include "lauxlib.h"
    #include "lualib.h"
}

int main(int argc, char ** argv){
    lua_State* L = luaL_newstate();
    luaL_openlibs(L);
    
    if (luaL_loadfile(L, "script.lua") != LUA_OK)
        std::cerr << lua_tostring(L, -1) << std::endl;
    lua_pcall(L, 0, 0, 0);
    
    lua_getglobal(L, "privet");
    if (lua_pcall(L, 0, 0, 0) != LUA_OK)
        std::cerr << lua_tostring(L, -1) << std::endl;
    
    lua_close(L);
}
