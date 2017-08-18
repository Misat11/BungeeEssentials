package misat11.essentials.bungee.utils;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import misat11.essentials.api.APlayerPlaceholderProcessor;
import misat11.essentials.api.IPlaceholder;
import misat11.essentials.bungee.BungeeEssentials;
import misat11.essentials.bungee.UserConfig;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;

public class Placeholders {
	
    private static final Pattern pattern = Pattern.compile("(?is)(?=\\n)|(?:[&\u00A7](?<color>[0-9A-FK-OR]))|" +
            "(?:\\[(?<tag>/?(?:b|i|u|s|nocolor|nobbcode)|(?:url|command|hover|suggest|color)=(?<value>(?:(?:[^]\\[]*)\\[(?:[^]\\[]*)\\])*(?:[^]\\[]*))|/(?:url|command|hover|suggest|color))\\])|" +
            "(?:\\[(?<implicitTag>url|command|suggest)\\](?=(?<implicitValue>.*?)\\[/\\k<implicitTag>\\]))"); 

	public static BaseComponent[] replace(String basestring, Object... placeholders) {
		for (Object p : placeholders) {
			if (p instanceof List) {
				for (Object pl_obj : (List<?>) p) {
					if (pl_obj instanceof IPlaceholder) {
						IPlaceholder pl = (IPlaceholder) pl_obj;
						basestring = basestring.replaceAll("%" + pl.baseString() + "%", pl.replace());
					}
				}
			} else if (p instanceof IPlaceholder) {
				IPlaceholder pl = (IPlaceholder) p;
				basestring = basestring.replaceAll("%" + pl.baseString() + "%", pl.replace());
			}
		}
		List<?> list = BungeeEssentials.getConfig().getList("regex");
		if (list != null) {
			for (Object entry : list) {
				Map<?, ?> map = (Map<?, ?>) entry;
				basestring = basestring.replaceAll(String.valueOf(map.get("search")),
						String.valueOf(map.get("replace")));
			}
		}

		basestring = basestring.replaceAll("\\&([0-9A-Za-z]+)", "§$1");
 
		return parseBBCode(basestring);
	}
	
	/* Parser from FreeBungeeChat by CodeCrafter47 */
	private static BaseComponent[] parseBBCode(String basestring) {
		Matcher matcher = pattern.matcher(basestring);
        TextComponent current = new TextComponent();
        List<BaseComponent> components = new LinkedList<>();
        int forceBold = 0;
        int forceItalic = 0;
        int forceUnderlined = 0;
        int forceStrikethrough = 0;
        int nocolorLevel = 0;
        int nobbcodeLevel = 0;
        Deque<ChatColor> colorDeque = new LinkedList<>();
        Deque<ClickEvent> clickEventDeque = new LinkedList<>();
        Deque<HoverEvent> hoverEventDeque = new LinkedList<>();
        while (matcher.find()) {
            boolean parsed = false;
            {
                StringBuffer stringBuffer = new StringBuffer();
                matcher.appendReplacement(stringBuffer, "");
                TextComponent component = new TextComponent(current);
                current.setText(stringBuffer.toString());
                components.add(current);
                current = component;
            }
            String group_color = matcher.group("color");
            String group_tag = matcher.group("tag");
            String group_value = matcher.group("value");
            String group_implicitTag = matcher.group("implicitTag");
            String group_implicitValue = matcher.group("implicitValue");
            if (group_color != null && nocolorLevel <= 0) {
                ChatColor color = ChatColor.getByChar(group_color.charAt(0));
                if (color != null) {
                    switch (color) {
                        case MAGIC:
                            current.setObfuscated(true);
                            break;
                        case BOLD:
                            current.setBold(true);
                            break;
                        case STRIKETHROUGH:
                            current.setStrikethrough(true);
                            break;
                        case UNDERLINE:
                            current.setUnderlined(true);
                            break;
                        case ITALIC:
                            current.setItalic(true);
                            break;
                        case RESET:
                            color = ChatColor.WHITE;
                        default:
                            current = new TextComponent();
                            current.setColor(color);
                            current.setBold(forceBold > 0);
                            current.setItalic(forceItalic > 0);
                            current.setUnderlined(forceUnderlined > 0);
                            current.setStrikethrough(forceStrikethrough > 0);
                            if (!colorDeque.isEmpty()) {
                                current.setColor(colorDeque.peek());
                            }
                            if (!clickEventDeque.isEmpty()) {
                                current.setClickEvent(clickEventDeque.peek());
                            }
                            if (!hoverEventDeque.isEmpty()) {
                                current.setHoverEvent(hoverEventDeque.peek());
                            }
                    }
                    parsed = true;
                }
            }
            if (group_tag != null && nobbcodeLevel <= 0) {
                // [b]this is bold[/b]
                if (group_tag.matches("(?is)^b$")) {
                    forceBold++;
                    if (forceBold > 0) {
                        current.setBold(true);
                    } else {
                        current.setBold(false);
                    }
                    parsed = true;
                } else if (group_tag.matches("(?is)^/b$")) {
                    forceBold--;
                    if (forceBold <= 0) {
                        current.setBold(false);
                    } else {
                        current.setBold(true);
                    }
                    parsed = true;
                }
                // [i]this is italic[/i]
                if (group_tag.matches("(?is)^i$")) {
                    forceItalic++;
                    if (forceItalic > 0) {
                        current.setItalic(true);
                    } else {
                        current.setItalic(false);
                    }
                    parsed = true;
                } else if (group_tag.matches("(?is)^/i$")) {
                    forceItalic--;
                    if (forceItalic <= 0) {
                        current.setItalic(false);
                    } else {
                        current.setItalic(true);
                    }
                    parsed = true;
                }
                // [u]this is underlined[/u]
                if (group_tag.matches("(?is)^u$")) {
                    forceUnderlined++;
                    if (forceUnderlined > 0) {
                        current.setUnderlined(true);
                    } else {
                        current.setUnderlined(false);
                    }
                    parsed = true;
                } else if (group_tag.matches("(?is)^/u$")) {
                    forceUnderlined--;
                    if (forceUnderlined <= 0) {
                        current.setUnderlined(false);
                    } else {
                        current.setUnderlined(true);
                    }
                    parsed = true;
                }
                // [s]this is crossed out[/s]
                if (group_tag.matches("(?is)^s$")) {
                    forceStrikethrough++;
                    if (forceStrikethrough > 0) {
                        current.setStrikethrough(true);
                    } else {
                        current.setStrikethrough(false);
                    }
                    parsed = true;
                } else if (group_tag.matches("(?is)^/s$")) {
                    forceStrikethrough--;
                    if (forceStrikethrough <= 0) {
                        current.setStrikethrough(false);
                    } else {
                        current.setStrikethrough(true);
                    }
                    parsed = true;
                }
                // [color=red]huh this is red...[/color]
                if (group_tag.matches("(?is)^color=.*$")) {
                    ChatColor color = null;
                    for (ChatColor color1 : ChatColor.values()) {
                        if (color1.getName().equalsIgnoreCase(group_value)) {
                            color = color1;
                        }
                    }
                    colorDeque.push(current.getColor());
                    if (color != null && color != ChatColor.BOLD && color != ChatColor.ITALIC && color != ChatColor.MAGIC
                            && color != ChatColor.RESET && color != ChatColor.STRIKETHROUGH && color != ChatColor.UNDERLINE) {
                        colorDeque.push(color);
                        current.setColor(color);
                    } else {
                        ProxyServer.getInstance().getLogger().warning("Invalid color tag: [" + group_tag + "] UNKNOWN COLOR '" + group_value + "'");
                        colorDeque.push(ChatColor.WHITE);
                        current.setColor(ChatColor.WHITE);
                    }
                    parsed = true;
                } else if (group_tag.matches("(?is)^/color$")) {
                    if (!colorDeque.isEmpty()) {
                        colorDeque.pop();
                        current.setColor(colorDeque.pop());
                    }
                    parsed = true;
                }
                // [url=....]
                if (group_tag.matches("(?is)^url=.*$")) {
                    String url = group_value;
                    url = url.replaceAll("(?is)\\[/?nobbcode\\]", "");
                    if (!url.startsWith("http")) {
                        url = "http://" + url;
                    }
                    ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, url);
                    clickEventDeque.push(clickEvent);
                    current.setClickEvent(clickEvent);
                    parsed = true;
                }
                // [/url], [/command], [/suggest]
                if (group_tag.matches("(?is)^/(?:url|command|suggest)$")) {
                    if (!clickEventDeque.isEmpty()) clickEventDeque.pop();
                    if (clickEventDeque.isEmpty()) {
                        current.setClickEvent(null);
                    } else {
                        current.setClickEvent(clickEventDeque.peek());
                    }
                    parsed = true;
                }
                // [command=....]
                if (group_tag.matches("(?is)^command=.*")) {
                    group_value = group_value.replaceAll("(?is)\\[/?nobbcode\\]", "");
                    ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, group_value);
                    clickEventDeque.push(clickEvent);
                    current.setClickEvent(clickEvent);
                    parsed = true;
                }
                // [suggest=....]
                if (group_tag.matches("(?is)^suggest=.*")) {
                    group_value = group_value.replaceAll("(?is)\\[/?nobbcode\\]", "");
                    ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, group_value);
                    clickEventDeque.push(clickEvent);
                    current.setClickEvent(clickEvent);
                    parsed = true;
                }
                // [hover=....]...[/hover]
                if (group_tag.matches("(?is)^hover=.*$")) {
                    BaseComponent[] components1 = parseBBCode(group_value);
                    if (!hoverEventDeque.isEmpty()) {
                        // why is there no apache commons lib in bungee
                        BaseComponent[] components2 = hoverEventDeque.getLast().getValue();
                        BaseComponent[] components3 = new BaseComponent[components1.length + components2.length + 1];
                        int i = 0;
                        for (BaseComponent baseComponent : components2) components3[i++] = baseComponent;
                        components3[i++] = new TextComponent("\n");
                        for (BaseComponent baseComponent : components1) components3[i++] = baseComponent;
                        components1 = components3;
                    }
                    HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, components1);
                    hoverEventDeque.push(hoverEvent);
                    current.setHoverEvent(hoverEvent);
                    parsed = true;
                } else if (group_tag.matches("(?is)^/hover$")) {
                    if (!hoverEventDeque.isEmpty()) hoverEventDeque.pop();
                    if (hoverEventDeque.isEmpty()) {
                        current.setHoverEvent(null);
                    } else {
                        current.setHoverEvent(hoverEventDeque.peek());
                    }
                    parsed = true;
                }
            }
            if (group_implicitTag != null && nobbcodeLevel <= 0) {
                // [url]spigotmc.org[/url]
                if (group_implicitTag.matches("(?is)^url$")) {
                    String url = group_implicitValue;
                    if (!url.startsWith("http")) {
                        url = "http://" + url;
                    }
                    ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, url);
                    clickEventDeque.push(clickEvent);
                    current.setClickEvent(clickEvent);
                    parsed = true;
                }
                // [command]/spawn[/command]
                if (group_implicitTag.matches("(?is)^command$")) {
                    ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, group_implicitValue);
                    clickEventDeque.push(clickEvent);
                    current.setClickEvent(clickEvent);
                    parsed = true;
                }
                // [suggest]/friend add [/suggest]
                if (group_implicitTag.matches("(?is)^suggest$")) {
                    ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, group_implicitValue);
                    clickEventDeque.push(clickEvent);
                    current.setClickEvent(clickEvent);
                    parsed = true;
                }
            }
            if (group_tag != null) {
                if (group_tag.matches("(?is)^nocolor$")) {
                    nocolorLevel++;
                    parsed = true;
                }
                if (group_tag.matches("(?is)^/nocolor$")) {
                    nocolorLevel--;
                    parsed = true;
                }
                if (group_tag.matches("(?is)^nobbcode$")) {
                    nobbcodeLevel++;
                    parsed = true;
                }
                if (group_tag.matches("(?is)^/nobbcode$")) {
                    nobbcodeLevel--;
                    parsed = true;
                }
            }
            if (!parsed) {
                TextComponent component = new TextComponent(current);
                current.setText(matcher.group(0));
                components.add(current);
                current = component;
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        matcher.appendTail(stringBuffer);
        current.setText(stringBuffer.toString());
        components.add(current);
        
        return components.toArray(new BaseComponent[components.size()]);
	}

	public static List<IPlaceholder> getPlayerPlaceholders(ProxiedPlayer player, String prefix) {
		return getPlayerPlaceholders(player.getName(), prefix);
	}

	public static List<IPlaceholder> getPlayerPlaceholders(String playername, String prefix) {
		List<IPlaceholder> list = new ArrayList<IPlaceholder>();
		if (prefix == null || prefix == "")
			prefix = "";
		else
			prefix = prefix + "_";

		ProxiedPlayer player = ProxyServer.getInstance().getPlayer(playername);
		pl(list, prefix + "name", playername);
		pl(list, prefix + "displayname", player != null ? player.getDisplayName() : playername);
		if (player != null) {
			Server server = player.getServer();
			if (server != null) {
				pl(list, prefix + "server", server.getInfo().getName());
				pl(list, prefix + "server_motd", server.getInfo().getName());
			} else {
				emptyPl(list, prefix + "server", prefix + "server_motd");
			}
		} else {
			emptyPl(list, prefix + "server", prefix + "server_motd");
		}
		pl(list, prefix + "customname", UserConfig.getPlayer(playername).getCustomname());
		if (BungeePermsData.isAvailable()) {
			pl(list, prefix + "BungeePerms_prefix", BungeePermsData.getPrefix(playername));
			pl(list, prefix + "BungeePerms_suffix", BungeePermsData.getSuffix(playername));
			pl(list, prefix + "BungeePerms_group", BungeePermsData.getGroup(playername));
		} else {
			emptyPl(list, prefix + "BungeePerms_prefix", prefix + "BungeePerms_suffix", prefix + "BungeePerms_group");
		}
		if (LuckPermsData.isAvailable()) {
			pl(list, prefix + "LuckPerms_prefix", LuckPermsData.getPrefix(playername));
			pl(list, prefix + "LuckPerms_suffix", LuckPermsData.getSuffix(playername));
			pl(list, prefix + "LuckPerms_group", LuckPermsData.getPrimaryGroup(playername));
		} else {
			emptyPl(list, prefix + "LuckPerms_prefix", prefix + "LuckPerms_suffix", prefix + "LuckPerms_group");
		}

		for (APlayerPlaceholderProcessor proc : BungeeEssentials.getInstance().getPlayerPlaceholderProcessors()) {
			proc.process(list, playername, prefix);
		}

		return list;
	}

	private static void pl(List<IPlaceholder> list, String baseString, String replace) {
		list.add(new Placeholder(baseString, replace));
	}

	private static void emptyPl(List<IPlaceholder> list, String... empty) {
		for (String e : empty) {
			list.add(new Placeholder(e, "none"));
		}
	}
}
